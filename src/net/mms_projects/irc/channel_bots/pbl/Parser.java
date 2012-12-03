package net.mms_projects.irc.channel_bots.pbl;

import java.util.Stack;

import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Parser {

	char[] endOfIdentifier = {' ', '\n'}; 
	char[] identifierArgumentStart = {'('};
	char[] identifierArgumentSeparator = {','};
	char[] identifierArgumentEnd = {')'};
	
	Stack<LanguageEntity> stack;
	
	public String parse(String rawdata) {
		stack = new Stack<LanguageEntity>();
		rawdata += "\n";
		
		boolean inIdentifier = false;
		boolean inArguments = false;
		boolean inArgument = false;
		boolean inName = false;
		boolean hadArguments = false;
		boolean spaceCancel = true;
		
		Identifier currentIdentifier = null;
		String currentArgument = "";
		for (char character : rawdata.toCharArray()) {
			if (!inIdentifier) {
				if (character == '$') {
					currentIdentifier = new Identifier();
					stack.add(currentIdentifier);
					inIdentifier = true;
					inName = true;
					spaceCancel = true;
					hadArguments = false;
					continue;
				}
			}
			if (inIdentifier) {
				if (contains(identifierArgumentStart, character)) {
					inArguments = true;
					inArgument = true;
					inName = false;
					hadArguments = true;
					spaceCancel = false;
					
					continue;
				}
				if (contains(identifierArgumentSeparator, character)) {
					currentIdentifier.arguments.add(currentArgument.trim());
					currentArgument = "";
					
					continue;
				}
				if (contains(identifierArgumentEnd, character)) {
					inArguments = false;
					inArgument = false;
					inIdentifier = false;
					
					currentIdentifier.arguments.add(currentArgument.trim());
					currentArgument = "";
					
					System.out.println(stack.pop().dump());
				}
				
				
				
				if ((spaceCancel) && (character == ' ')) {
					inIdentifier = false;
					inName = false;
					System.out.println(currentIdentifier.name);
					System.out.println(stack.pop().dump());
				}
				if (character == '\n') {
					inIdentifier = false;
					inName = false;
					System.out.println(currentIdentifier.name);
					System.out.println(stack.pop().dump());
				}
				
				if (inName) {
					currentIdentifier.name += character;
				}
				if (inArgument) {
					currentArgument += character;
				}
 			}
		}
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
	
}
