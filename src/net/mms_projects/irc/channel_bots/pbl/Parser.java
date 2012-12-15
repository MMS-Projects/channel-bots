package net.mms_projects.irc.channel_bots.pbl;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;
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
		System.out.println("Input: " + rawdata);

		rawdata += "\n";

		int identifierType = 0;

		Identifier currentIdentifier = null;
		Variable currentVariable = null;

		List<Character> input = new ArrayList<Character>();
		for (Character c : rawdata.toCharArray()) {
			input.add(c);
		}
		int dataStart = 0;
		int identifierStart = 0;
		int entityStart = 0;
		int parenthesesCount = 0;

		System.out.println(currentEvalation + " - " + maxEvaluations);
		if ((maxEvaluations < 0) || (currentEvalation < maxEvaluations)) {
			for (int i = 0; i < input.size(); ++i) {
				if (input.get(i) == '$') {
					if ((input.size() > i + 1) && (input.get(i + 1) == '+')
							&& (input.size() > i + 2)
							&& (input.get(i + 2) != '(')) {
						List<Character> replacement = new ArrayList<Character>();
						replacement.add(' ');
						replacement.add('\u5001');
						replacement.add(' ');
						input = replacePart(input, i - 1, i + 3, replacement);
						continue;
					}
					if (identifierType == 0) {
						currentIdentifier = new Identifier();
						dataStart = i;
						identifierStart = i;

						identifierType = Identifier.TYPE_NORMAL;
					}
				}
				if (currentIdentifier == null) {
					if (input.get(i) == '%') {
						currentVariable = new Variable();
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
				if (input.get(i) == ',') {
					if ((identifierType == Identifier.TYPE_PARAMETERS)
							&& (parenthesesCount == 1)) {
						currentIdentifier.arguments.add(this.eval(
								getPart(input, dataStart + 1, i),
								currentEvalation + 1, maxEvaluations));
						dataStart = i;
					}
				}
				if (input.get(i) == ')') {
					if ((identifierType == Identifier.TYPE_PARAMETERS)
							&& (parenthesesCount == 1)) {
						currentIdentifier.arguments.add(this.eval(
								getPart(input, dataStart + 1, i),
								currentEvalation + 1, maxEvaluations));

						if ((input.size() > i + 1) && (input.get(i + 1) == '.')) {
							dataStart = i + 1;

							identifierType = Identifier.TYPE_PROPERTY;
						} else {
							currentIdentifier.unparsed = getPart(input,
									identifierStart, i + 1);

							List<Character> output = this.handler
									.handle(currentIdentifier);
							currentIdentifier.dump();
							input = replacePart(input, identifierStart, i + 1,
									output);
							i = identifierStart;

							currentIdentifier = null;
							identifierType = 0;
						}
					}
					--parenthesesCount;
				}
				if ((input.get(i) == ' ') || (input.get(i) == '\n')) {
					if (identifierType == Identifier.TYPE_NORMAL) {
						currentIdentifier.name = getPart(input, dataStart + 1,
								i);
						currentIdentifier.unparsed = getPart(input,
								identifierStart, i);

						List<Character> output = this.handler
								.handle(currentIdentifier);
						currentIdentifier.dump();
						input = replacePart(input, identifierStart, i, output);
						i = identifierStart;

						currentIdentifier = null;
						identifierType = 0;
					}
					if (identifierType == Identifier.TYPE_PROPERTY) {
						currentIdentifier.property = getPart(input,
								dataStart + 1, i);
						currentIdentifier.unparsed = getPart(input,
								identifierStart, i);

						List<Character> output = this.handler
								.handle(currentIdentifier);
						currentIdentifier.dump();
						input = replacePart(input, identifierStart, i, output);
						i = identifierStart;

						currentIdentifier = null;
						identifierType = 0;
					}
					if (currentVariable != null) {
						currentVariable.name = getPart(input, entityStart + 1,
								i);

						List<Character> output = this.handler
								.handle(currentVariable);
						System.out.println(currentVariable.dump());
						input = replacePart(input, entityStart, i, output);
						i = entityStart;

						currentVariable = null;
					}
				}
			}
		}

		rawdata = getPart(input, 0, input.size() - 1).trim();
		rawdata = rawdata.replace(" \u5001 ", "");

		System.out.println("Output: " + rawdata);
		System.out.println("----------");
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
