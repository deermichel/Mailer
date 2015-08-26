/*
Mailer - A simple mail distribution tool (e.g. for newsletters)
Copyright (C) 2015 Micha Hanselmann

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.mh.mailer;

import java.util.HashMap;

public class Localizer {
	
	private static HashMap<String, String> localized = new HashMap<>();
	
	
	public static void init() {
		
		// add words
		if (System.getProperty("user.language").equals("de")) {
			
			// german
			localized.put("From", "Absender");
			localized.put("Subject", "Betreff");
			localized.put("Username", "Benutzername");
			localized.put("Password", "Passwort");
			localized.put("Interval (min)", "Intervall (min)");
			localized.put("Mails per interval", "Mails per Intervall");
			localized.put("Load recipients list", "Adressliste laden");
			localized.put("Load message", "Nachricht laden");
			localized.put("Start", "Starten");
			localized.put("Stop", "Stoppen");
			localized.put("Error", "Fehler");
			localized.put("No recipients loaded", "Keine Adressen geladen");
			localized.put("No message loaded", "Keine Nachricht geladen");
			localized.put("No host entered\n", "Kein Host angegeben\n");
			localized.put("No username entered\n", "Kein Benutzername angegeben\n");
			localized.put("No password entered\n", "Kein Passwort angegeben\n");
			localized.put("No from entered\n", "Kein Absender angegeben\n");
			localized.put("No subject entered\n", "Kein Betreff angegeben\n");
			localized.put("Offset too high", "Offset zu hoch");
			
		}
		
	}
	
	public static String get(String id) {
		return localized.getOrDefault(id, id);
	}

}
