package net.mms_projects.irc.channel_bots;

import java.util.logging.Logger;

public class Tokenizer {

	String[] tokens = null;
	String separator = null;

	public static void main(String[] args) {
		Tokenizer tok = new Tokenizer("a.b.c.d.e", "\\.");
		System.out.println("Output: " + tok.getToken("3"));
		System.out.println("Output: " + tok.getToken("9"));
		System.out.println("Output: " + tok.getToken("2-"));
		System.out.println("Output: " + tok.getToken("2-4"));
		System.out.println("-");
		System.out.println("Output: " + tok.isToken("3"));
		System.out.println("Output: " + tok.isToken("9"));
	}

	public Tokenizer(String data, String separator) {
		this.tokens = data.split(separator);
		this.separator = separator;
	}

	public String getToken(String token) {
		int[] tokenData = this.parseTokenData(token);
		int start = tokenData[0];
		int end = tokenData[1];

		String data = "";
		for (int i = start; i <= end; i++) {
			try {
				data += this.tokens[i];
				if (start != end) {
					data += this.separator;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				if (data.equals("")) {
					data += null;
				}
			}
		}
		return data;
	}

	public String getToken(int token) {
		return this.getToken(Integer.toString(token));
	}

	public boolean isToken(String token) {
		int[] tokenData = this.parseTokenData(token);
		int start = tokenData[0];
		int end = tokenData[1];
		String data = "";
		for (int i = start; i <= end; i++) {
			try {
				data += this.tokens[i];
			} catch (ArrayIndexOutOfBoundsException e) {
				return false;
			}
		}
		return true;
	}

	public boolean isToken(int token) {
		return this.isToken(Integer.toString(token));
	}

	private int[] parseTokenData(String token) {
		boolean followUp = false;
		String tokenStart = "";
		String tokenEnd = "";
		for (char c : token.toCharArray()) {
			switch (c) {
			case '-':
				followUp = true;
				tokenEnd = "";
				break;
			default:
				if (!followUp) {
					tokenStart += c;
					tokenEnd += c;
				} else {
					tokenEnd += c;
				}
			}
		}
		int start = Integer.parseInt(tokenStart) - 1;
		int end = (!tokenEnd.equals("")) ? Integer.parseInt(tokenEnd) - 1
				: this.tokens.length - 1;
		Logger.getGlobal().info(
				"Tokenizer: Start: " + tokenStart + " - End: " + tokenEnd
						+ " - Follow up: " + followUp);
		return new int[] { start, end };
	}
	
	public int size() {
		return this.tokens.length;
	}

}
