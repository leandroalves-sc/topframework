package com.topsoft.topframework.swing;

import java.awt.Component;

import javax.swing.JOptionPane;

public class LazAlert{

	public static void showInfo( String message ){
		JOptionPane.showMessageDialog( null, message, "Information", JOptionPane.INFORMATION_MESSAGE );
	}
	
	public static void showInfo( String message, String title ){
		JOptionPane.showMessageDialog( null, message, title, JOptionPane.INFORMATION_MESSAGE );
	}
	
	public static void showInfo( Component parent, String message ){
		JOptionPane.showMessageDialog( parent, message, "Information", JOptionPane.INFORMATION_MESSAGE );
	}
	
	public static void showInfo( Component parent, String message, String title ){
		JOptionPane.showMessageDialog( parent, message, title, JOptionPane.INFORMATION_MESSAGE );
	}
	
	public static void showWarning( String message ){
		JOptionPane.showMessageDialog( null, message, "Warning", JOptionPane.WARNING_MESSAGE );
	}
	
	public static void showWarning( Component parent, String message ){
		JOptionPane.showMessageDialog( parent, message, "Warning", JOptionPane.WARNING_MESSAGE );
	}
	
	public static void showWarning( Component parent, String message, String title ){
		JOptionPane.showMessageDialog( parent, message, title, JOptionPane.WARNING_MESSAGE );
	}

	public static void showError( String message ){
		JOptionPane.showMessageDialog( null, message, "Error", JOptionPane.ERROR_MESSAGE );
	}
	
	public static void showError( String message, String title ){
		JOptionPane.showMessageDialog( null, message, title, JOptionPane.ERROR_MESSAGE );
	}
	
	public static void showError( Component parent, String message ){
		JOptionPane.showMessageDialog( parent, message, "Erro", JOptionPane.ERROR_MESSAGE );
	}
	
	public static void showError( Component parent, String message, String title ){
		JOptionPane.showMessageDialog( parent, message, title, JOptionPane.ERROR_MESSAGE );
	}
	
	public static int showQuestion( String message ){
		return JOptionPane.showConfirmDialog( null, message, "Confirm", JOptionPane.YES_NO_OPTION );
	}	
	
	public static int showQuestion( Component parent, String message ){
		return JOptionPane.showConfirmDialog( parent, message, "Confirm", JOptionPane.YES_NO_OPTION );
	}
	
	public static int showQuestion( String message, String title ){
		return showQuestion( null, message, title );
	}	

	public static int showQuestion( Component parent, String message, String title ){
		return JOptionPane.showConfirmDialog( parent, message, title, JOptionPane.YES_NO_OPTION );
	}
}
