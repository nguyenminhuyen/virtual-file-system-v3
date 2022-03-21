package com.assignment.virtualfilesystem.validate;

public enum EValidCommand {
	cr, cat, ls, mv, rm, up;

	public static boolean contains(String s) {
		for (EValidCommand cmd : values())
			if (cmd.name().equals(s))
				return true;
		return false;
	}
}
