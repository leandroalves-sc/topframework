package com.topsoft.topframework.swing.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.text.JTextComponent;

import com.topsoft.topframework.swing.LazLabel;
import com.topsoft.topframework.swing.LazSwitch;
import com.topsoft.topframework.swing.fonts.LazFonts;

public class LazSwingUtils{

	public static void centerWindow( Window view ){
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        Dimension frameSize = view.getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        
        view.setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2 );
    }
	
	public static Image resizeImage( Image srcImg, int width, int height ){
		
		BufferedImage resizedImg = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
		g2.drawImage( srcImg, 0, 0, width, height, null );
		g2.dispose();

		return resizedImg;
	}
	
	public static void setEnabled( Container container, boolean enabled ){
		
		for( Component comp : getAllComponents( container ) ){
			
			if( comp instanceof JTextField )
				((JTextField)comp).setEditable( enabled );
			else if( comp instanceof JTextArea )
				((JTextArea)comp).setEditable( enabled );
			else if( !(comp instanceof JLabel) )
				comp.setEnabled( enabled );
		}
	}
	
	public static List<Component> getAllComponents( Container container ){
		
		List<Component> components = new ArrayList<Component>();
		
		for( Component comp : container.getComponents() ){
			
			if( comp instanceof LazSwitch )
				components.add( comp );
			else if( comp instanceof JPanel || comp instanceof JScrollPane || comp instanceof JViewport || comp instanceof JRootPane || comp instanceof JLayeredPane || comp instanceof JMenuBar )
				components.addAll( getAllComponents( (Container) comp ) );
			else if( comp instanceof JTabbedPane ){
				
                JTabbedPane tp = (JTabbedPane) comp;
                
                for( int i = 0; i < tp.getTabCount(); i++ )
                	components.addAll( getAllComponents( (Container) tp.getComponentAt(i) ) );
            }
			else if( comp instanceof JComponent )
				components.add( comp );
		}
		
		return components;
	}
	
	public static JComponent getFirstTextComponent( Container container ){
		
		for( Component component : container.getComponents() ){
			
			if( component instanceof JRootPane || component instanceof JPanel || component instanceof JTabbedPane ){
				
				JComponent first = getFirstTextComponent( (Container) component );
				
				if( first != null )
					return first;
			}
			else if( component instanceof JTextComponent || component instanceof JComboBox || component instanceof JCheckBox )
				return (JComponent) component;
		}
		
		return null;
	}	
	
	@SuppressWarnings("unchecked")
	public static <T extends Container> T getParent( Component comp, Class<T> parentClass ){
		
		if( comp == null )
			return null;
		else if( parentClass.isAssignableFrom( comp.getClass() ) )
			return (T) comp;
		
		return getParent( comp.getParent(), parentClass );
	}	
	
	public static void addSeparator( Container panel, String text ){
		
		panel.add( new LazLabel( text, LazFonts.BASE_FONT_BOLD ), "gaptop 5, gapbottom 1, span, split 2, aligny center" );
		panel.add( new JSeparator(), "gaptop 5, gapleft 10, growx, aligny center");
	}
	
	public static void addOldSeparator( Container panel, String text ){
		
		panel.add( new LazLabel( text ), "gaptop 5, gapbottom 1, span, split 2, aligny center" );
		panel.add( new JSeparator(), "gaptop 5, gapleft 10, growx, aligny center");
	}
	
    public static void showWaitCursor( Container container ){
        
    	if( container != null )
        	container.setCursor( LazSwingConstants.WAIT_CURSOR );
    }
    
    public static void showHandCursor( Container container ){
        
    	if( container != null )
    		container.setCursor( LazSwingConstants.HAND_CURSOR );
    }    
    
    public static void showDefaultCursor( Container container ){
    	
    	if( container != null )
    		container.setCursor( LazSwingConstants.DEFAULT_CURSOR );
    }
    
    public static void showCursor( Container container, Cursor cursor ){
    	
    	if( container != null )
    		container.setCursor( cursor );
    }    
}