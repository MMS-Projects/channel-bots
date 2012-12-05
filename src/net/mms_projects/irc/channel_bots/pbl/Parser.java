package net.mms_projects.irc.channel_bots.pbl;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.pbl.language_entities.Append;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Rawdata;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Text;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Variable;

public class Parser {

	private Handler handler;

	char[] endOfIdentifier = { ' ', '\n' };
	char[] identifierArgumentStart = { '(' };
	char[] identifierArgumentSeparator = { ',' };
	char[] identifierArgumentEnd = { ')' };

	public Parser(Handler handler) {
		this.handler = handler;

		for (Function function : this.handler.functions) {
			function.setParser(this);
		}
	}

	public String eval(String rawdata) {
		return eval(rawdata, -1);
	}

	public String eval(String rawdata, int maxEvaluations) {
		return eval(rawdata, 0, maxEvaluations);
	}

	private String eval(String rawdata, int currentEvalation, int maxEvaluations) {
		// System.out.println("Input: " + rawdata);

		Rawdata data = new Rawdata(this.handler, this);

		rawdata += "\n";

		int identifierType = 0;

		Identifier currentIdentifier = null;
		Variable currentVariable = null;
		Text currentText = new Text(handler, this);
		LanguageEntity currentEntity = currentText;

		List<Character> input = new ArrayList<Character>();
		for (Character c : rawdata.toCharArray()) {
			input.add(c);
		}
		int dataStart = 0;
		int entityStart = 0;
		int parenthesesCount = 0;

		System.out.println(currentEvalation + " - " + maxEvaluations);
		if ((maxEvaluations < 0) || (currentEvalation < maxEvaluations)) {
			for (int i = 0; i < input.size(); ++i) {
				if (input.get(i) == '$') {
					if (currentEntity instanceof Text) {
						if ((input.size() > i + 1) && (input.get(i + 1) == '+')
								&& (input.size() > i + 2)
								&& (input.get(i + 2) != '(')) {
							
							data.addEntity(currentEntity);
							data.addEntity(new Append(handler, this));
	
							currentIdentifier = null;
							currentVariable = null;
							currentText = new Text(handler, this);
							currentEntity = currentText;
	
							i++;
							continue;
						}
						boolean isIdentifier = true;
						if ((input.size() > i + 1) && (input.get(i + 1) == ' ')) {
							isIdentifier = false;
						}
						if ((input.size() > i + 1) && (input.get(i + 1) == '\n')) {
							isIdentifier = false;
						}
						
						if (isIdentifier) {
							data.addEntity(currentEntity);
	
							currentIdentifier = new Identifier(this.handler, this);
							currentVariable = null;
							currentText = null;
							currentEntity = currentIdentifier;
	
							dataStart = i;
							entityStart = i;
	
							identifierType = Identifier.TYPE_NORMAL;
						}
					}
				}
				if (currentEntity instanceof Text) {
					if (input.get(i) == '%') {
						data.addEntity(currentEntity);

						currentIdentifier = null;
						currentVariable = new Variable(this.handler, this);
						currentText = null;
						currentEntity = currentVariable;

						entityStart = i;
					}
				}
				if (input.get(i) == '(') {
					++parenthesesCount;
					if ((identifierType == Identifier.TYPE_NORMAL)
							&& (parenthesesCount == 1)) {
						currentIdentifier.name = getPart(input, dataStart + 1,
								i);
						dataStart = i;

						identifierType = Identifier.TYPE_PARAMETERS;
					}
				}
				if (currentEntity instanceof Identifier) {
					if (input.get(i) == ',') {
						if ((identifierType == Identifier.TYPE_PARAMETERS)
								&& (parenthesesCount == 1)) {
							System.out.println(currentIdentifier);
							System.out.println(currentEntity.getClass()
									.getSimpleName());
							currentIdentifier.arguments.add(getPart(input,
									dataStart + 1, i));
							dataStart = i;
						}
					}
					if (input.get(i) == ')') {
						if ((identifierType == Identifier.TYPE_PARAMETERS)
								&& (parenthesesCount == 1)) {
							currentIdentifier.arguments.add(getPart(input,
									dataStart + 1, i));

							if ((input.size() > i + 1)
									&& (input.get(i + 1) == '.')) {
								dataStart = i + 1;

								identifierType = Identifier.TYPE_PROPERTY;
							} else {
								currentIdentifier.unparsed = getPart(input,
										entityStart, i + 1);

								data.addEntity(currentEntity);

								i++;

								currentIdentifier = null;
								currentVariable = null;
								currentText = new Text(handler, this);
								currentEntity = currentText;

								identifierType = 0;
							}
						}
						--parenthesesCount;
					}
				}
				if ((input.get(i) == ' ') || (input.get(i) == '\n')) {
					if (identifierType == Identifier.TYPE_NORMAL) {
						currentIdentifier.name = getPart(input, dataStart + 1,
								i);
						currentIdentifier.unparsed = getPart(input,
								entityStart, i);

						data.addEntity(currentEntity);

						currentIdentifier = null;
						currentVariable = null;
						currentText = new Text(handler, this);
						currentEntity = currentText;

						identifierType = 0;
					}
					if (identifierType == Identifier.TYPE_PROPERTY) {
						currentIdentifier.property = getPart(input,
								dataStart + 1, i);
						currentIdentifier.unparsed = getPart(input,
								entityStart, i);

						data.addEntity(currentEntity);

						currentIdentifier = null;
						currentVariable = null;
						currentText = new Text(handler, this);
						currentEntity = currentText;

						identifierType = 0;
					}
					if (currentVariable != null) {
						currentVariable.name = getPart(input, entityStart + 1,
								i);

						data.addEntity(currentEntity);

						currentIdentifier = null;
						currentVariable = null;
						currentText = new Text(handler, this);
						currentEntity = currentText;
					}
				}
				if (currentEntity instanceof Text) {
					currentText.text += input.get(i);
				}
			}
			data.addEntity(currentText);
		}

		rawdata = data.getOutput().trim();
		rawdata = rawdata.replace(" \u5001 ", "");

		// System.out.println("Output: " + rawdata);
		// System.out.println("----------");

		data.dump();
		System.out.println("Output: " + data.getOutput().trim());

		return rawdata;
	}

	public boolean contains(char[] heystack, char needle) {
		for (char entity : heystack) {
			if (entity == needle) {
				return true;
			}
		}
		return false;
	}

	public String getPart(List<Character> heystack, int start, int end) {
		String value = "";
		for (int i = start; i < end; ++i) {
			value += heystack.get(i);
		}
		return value;
	}

	public List<Character> getPartList(List<Character> heystack, int start,
			int end) {
		List<Character> list = new ArrayList<Character>();
		for (int i = start; i < end; ++i) {
			list.add(heystack.get(i));
		}
		return list;
	}

	public List<Character> replacePart(List<Character> heystack, int start,
			int end, List<Character> replacement) {
		for (int i = start; i < end; ++i) {
			heystack.remove(start);
		}
		heystack.addAll(start, replacement);
		return heystack;
	}

}
