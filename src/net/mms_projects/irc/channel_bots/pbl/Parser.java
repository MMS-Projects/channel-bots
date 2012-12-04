package net.mms_projects.irc.channel_bots.pbl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Parser {

	private Handler handler;
	
	char[] endOfIdentifier = {' ', '\n'}; 
	char[] identifierArgumentStart = {'('};
	char[] identifierArgumentSeparator = {','};
	char[] identifierArgumentEnd = {')'};
	
	public Parser(Handler handler) {
		this.handler = handler;
	}
	
	public String parse(String rawdata) {
		System.out.println("Input: " + rawdata);
		
		rawdata += "\n";
		
		int identifierType = 0;
		
		Identifier currentIdentifier = null;
		
		List<Character> input = new ArrayList<Character>(); 
		for (Character c : rawdata.toCharArray()) {
			input.add(c);
		}
		int dataStart = 0;
		int identifierStart = 0;
		int parenthesesCount = 0;
		
		for (int i = 0; i < input.size(); ++i) {
			if (input.get(i) == '$') {
				if (identifierType == 0) {
					currentIdentifier = new Identifier();
					dataStart = i;
					identifierStart = i;
					
					identifierType = Identifier.TYPE_NORMAL;
				}
			}
			if (input.get(i) == '(') {
				++parenthesesCount;
				if ((identifierType == Identifier.TYPE_NORMAL) && (parenthesesCount == 1)) {
					currentIdentifier.name = getPart(input, dataStart + 1, i);
					dataStart = i;
					
					identifierType = Identifier.TYPE_PARAMETERS;
				}
			}
			if (input.get(i) == ',') {
				if (identifierType == Identifier.TYPE_PARAMETERS) {
					currentIdentifier.arguments.add(this.parse(getPart(input, dataStart + 1, i)));
					dataStart = i;
				}
			}
			if (input.get(i) == ')') {
				if ((identifierType == Identifier.TYPE_PARAMETERS) && (parenthesesCount == 1)) {
					currentIdentifier.arguments.add(this.parse(getPart(input, dataStart + 1, i)));
					
					if ((input.size() > i + 1) && (input.get(i + 1) == '.')) {						
						dataStart = i + 1;
						
						identifierType = Identifier.TYPE_PROPERTY;
					} else {
						currentIdentifier.unparsed = getPart(input, identifierStart, i + 1);
						
						List<Character> output = this.handler.handle(currentIdentifier);
						System.out.println("Parenthese ending " + output.size() + " - " + (i - identifierStart));
						System.out.println(currentIdentifier.dump());
						input = replacePart(input, identifierStart, i + 1, output);
						i = identifierStart;
						
						currentIdentifier = null;
						identifierType = 0;
					}
				}
				--parenthesesCount;
			}
			if ((input.get(i) == ' ') || (input.get(i) == '\n')) {
				if (identifierType == Identifier.TYPE_NORMAL) {
					currentIdentifier.name = getPart(input, dataStart + 1, i);
					currentIdentifier.unparsed = getPart(input, identifierStart, i);
					
					System.out.println("Normal ending");
					List<Character> output = this.handler.handle(currentIdentifier);
					System.out.println(currentIdentifier.dump());
					input = replacePart(input, identifierStart, i, output);
					i = i - (output.size() + 1);
					
					currentIdentifier = null;
					identifierType = 0;
				}
				if (identifierType == Identifier.TYPE_PROPERTY) {
					currentIdentifier.property = getPart(input, dataStart + 1, i);
					currentIdentifier.unparsed = getPart(input, identifierStart, i);
					
					System.out.println("Property ending");
					List<Character> output = this.handler.handle(currentIdentifier);
					System.out.println(currentIdentifier.dump());
					input = replacePart(input, identifierStart, i, output);
					i = i - (output.size() + 1);
					
					currentIdentifier = null;
					identifierType = 0;
				}
			}
		}
		
		rawdata = getPart(input, 0, input.size() - 1).trim();
		
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
	
	public List<Character> getPartList(List<Character> heystack, int start, int end) {
		List<Character> list = new ArrayList<Character>();
		for (int i = start; i < end; ++i) {
			list.add(heystack.get(i));
		}
 		return list;
	}
	
	public List<Character> replacePart(List<Character> heystack, int start, int end, List<Character> replacement) {
		for (int i = start; i < end; ++i) {
			heystack.remove(start);
		}
		heystack.addAll(start, replacement);
		return heystack;
	}
	
}
