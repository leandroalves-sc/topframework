package com.topsoft.topframework.swing.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class LazFonts{
	
	public static Font BASE_FONT = new Font( "Nunito-Light", Font.PLAIN, 13 );
	public static Font BASE_FONT_BOLD = new Font( "Nunito-Regular", Font.PLAIN, 13 );
	
	static{
		
		String[] fonts = new String[]{ 
			"Nunito-Regular.ttf", //Nunito-Regular 
			"Nunito-Light.ttf",   //Nunito-Light
			"HelveticaLT63HE.ttf" //HelveticaNeue LT 63 MdEx Heavy 
		};
		
		for( String fontName : fonts ){
			
			try {
				
				Font font = Font.createFont( Font.TRUETYPE_FONT, LazFonts.class.getResourceAsStream( "/fonts/" + fontName ) );
				
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont( font );
				
				System.out.println( font.getName() );
			}
			catch (FontFormatException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}