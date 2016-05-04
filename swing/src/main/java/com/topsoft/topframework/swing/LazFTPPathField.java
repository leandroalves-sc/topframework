package com.topsoft.topframework.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import com.alee.extended.progress.WebProgressOverlay;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.utils.FileUtils;
import com.alee.utils.SwingUtils;
import com.alee.utils.SystemUtils;
import com.topsoft.topframework.base.exception.BusinessException;
import com.topsoft.topframework.base.util.LazImage;
import com.topsoft.topframework.ftp.domain.FTPConfiguration;
import com.topsoft.topframework.ftp.domain.FTPFile;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.event.LazEvent;
import com.topsoft.topframework.swing.event.LazFormEvent;
import com.topsoft.topframework.swing.event.LazFormListener;
import com.topsoft.topframework.swing.event.LazPathFieldEvent;
import com.topsoft.topframework.swing.event.LazPathFieldListener;
import com.topsoft.topframework.swing.view.FTPConnectionView;

public class LazFTPPathField extends WebProgressOverlay implements LazPathFieldListener {

	private static final long serialVersionUID = -8336128102103862268L;

	private LazFTPPathFieldInternal pathField;

	public LazFTPPathField() {

		setConsumeEvents(false);
		setComponent(pathField = new LazFTPPathFieldInternal());

		pathField.addActionListener(this);
	}

	public void setSelectedPath(File selectedPath) {
		pathField.setSelectedPath(selectedPath);
	}

	public File getSelectedPath() {
		return pathField.getSelectedPath();
	}

	public FTPConfiguration getFtpConfig() {
		return pathField.getFtpConfig();
	}

	public void setFtpConfig(FTPConfiguration ftpConfig) {
		pathField.setFtpConfig(ftpConfig);
	}

	public void disconnect() {
		pathField.disconnect();
	}

	@Override
	public void onConnecting(LazPathFieldEvent event) {
		setShowLoad(true);
	}

	@Override
	public void onConnected(LazPathFieldEvent event) {
		setShowLoad(false);
	}

	@Override
	public void onDisconnected(LazPathFieldEvent event) {
		setShowLoad(false);
	}

	private class LazFTPPathFieldInternal extends LazPathField implements ActionListener, LazFormListener, LazActionDispatcher<LazPathFieldListener> {

		private static final long serialVersionUID = 4955396719709515408L;

		private StandardFileSystemManager manager;;
		private FileSystemOptions opts;

		private FTPConfiguration ftpConfig;
		private FTPConnectionView view;
		private LazButton btnConnect;

		public LazFTPPathFieldInternal() {
			this(new FTPConfiguration());
		}

		public LazFTPPathFieldInternal(FTPConfiguration ftpConfig) {

			super();

			this.ftpConfig = ftpConfig;

			btnConnect = new LazButton(LazImage.CONNECT);
			btnConnect.setRound(getRound());
			btnConnect.setLeftRightSpacing(StyleConstants.smallLeftRightSpacing);
			btnConnect.setDrawFocus(false);
			btnConnect.setDrawLeft(false);
			btnConnect.setDrawLeftLine(true);
			btnConnect.setRolloverDarkBorderOnly(false);
			btnConnect.setShadeWidth(0);
			btnConnect.addActionListener(this);

			add(btnConnect, BorderLayout.LINE_END);

			setSelectedPath(new FTPFile("FTP:\\"));
		}

		@Override
		public void addActionListener(LazPathFieldListener listener) {
			listenerList.add(LazPathFieldListener.class, listener);
		}

		@Override
		public void removeActionListener(LazPathFieldListener listener) {
			listenerList.remove(LazPathFieldListener.class, listener);
		}

		@Override
		public LazPathFieldListener[] getActionListeners() {
			return (LazPathFieldListener[]) (listenerList.getListeners(LazPathFieldListener.class));
		}

		@Override
		protected void startEditing() {

			// N�o permite edi��o direta no Field
		}

		@Override
		protected File[] getFileChilds(File file) {

			List<File> files = new ArrayList<File>();

			if (file == null || file.getPath().equals("C:\\"))
				files.add(new FTPFile("FTP:\\"));
			else {

				try {

					if (manager != null) {

						String folder = file.getName();

						if ("FTP:".equals(folder))
							folder = "";

						String sftpUri = ftpConfig.getBaseURL() + "/" + folder;
						FileObject remoteFolder = manager.resolveFile(sftpUri, opts);

						if (remoteFolder != null) {
							for (FileObject remoteFile : remoteFolder.getChildren()) {
								if (remoteFile.getType() == FileType.FOLDER) {
									files.add(new FTPFile(remoteFile.getName().getPath()));
								}
							}
						}
					}
				}
				catch (Exception e) {
					return new FTPFile[]{};
				}
			}

			return files.toArray(new File[files.size()]);
		}

		@Override
		protected void folderSelected(File folder) {

			updatePath(new FTPFile(folder == null ? "FTP:\\" : folder.getPath()));
			fireDirectoryChanged(folder);
			this.transferFocus();
		}

		protected synchronized void updatePath(final File path) {

			// Saving new path
			selectedPath = new FTPFile(path.getPath());

			// Clearing old path components
			pathField.removeFocusListener(pathFocusListener);
			contentPanel.removeAll();

			// Determining oriention
			final boolean ltr = LazFTPPathFieldInternal.this.getComponentOrientation().isLeftToRight();

			// Determining root
			if (SystemUtils.isWindows()) {

				final WebButton computerButton = getMyComputer();
				contentPanel.add(computerButton);
				contentPanel.add(getRootsArrowButton(ltr));
			}

			if (selectedPath != null) {

				// Creating parents list
				File folder = new FTPFile(selectedPath.getAbsolutePath());
				final List<File> parents = new ArrayList<File>();
				parents.add(0, folder);

				while (folder.getParent() != null) {
					folder = folder.getParentFile();
					parents.add(0, folder);
				}

				// Adding path buttons
				boolean first = true;

				for (final File file : parents) {

					final WebButton wb = new WebButton();
					wb.setRound(!SystemUtils.isWindows() && first ? StyleConstants.smallRound : 0);
					wb.setShadeWidth(0);
					wb.setLeftRightSpacing(0);
					wb.setRolloverDecoratedOnly(true);
					wb.setRolloverDarkBorderOnly(false);
					wb.setFocusable(false);

					if (!SystemUtils.isWindows() && first) {
						wb.setIcon(FileUtils.getMyComputerIcon());
						wb.putClientProperty(FILE_ICON, FileUtils.getMyComputerIcon());
					}
					else {
						wb.setText(fsv.getSystemDisplayName(file));
						wb.putClientProperty(FILE_ICON, FileUtils.getFileIcon(file, false));
					}
					wb.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(final ActionEvent e) {

							folderSelected(file);
						}
					});

					contentPanel.add(wb);

					int childsCount = 0;
					final WebPopupMenu menu = new WebPopupMenu();
					final File[] files = FileUtils.sortFiles(getFileChilds(file));

					if (files != null) {

						for (final File root : files) {

							if (root.isDirectory()) {

								final WebMenuItem menuItem = new WebMenuItem(FileUtils.getDisplayFileName(root));
								menuItem.setIcon(FileUtils.getFileIcon(root, false));
								menuItem.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(final ActionEvent e) {

										folderSelected(root);
									}
								});

								menu.add(menuItem);
								childsCount++;
							}
						}
					}

					if (!SystemUtils.isWindows() && first) {
						setRootsMenu(menu, childsCount);
					}

					final WebToggleButton childs = new WebToggleButton();
					childs.setIcon(ltr ? right : left);
					childs.setSelectedIcon(down);
					childs.setShadeToggleIcon(false);
					childs.setRound(0);
					childs.setShadeWidth(0);
					childs.setRolloverDecoratedOnly(true);
					childs.setRolloverDarkBorderOnly(false);
					childs.setFocusable(false);
					childs.setComponentPopupMenu(menu);
					childs.setMargin(0);
					childs.setLeftRightSpacing(0);
					childs.setEnabled(childsCount > 0);
					childs.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(final ActionEvent e) {

							// todo Apply orientation globally on change, not
							// here
							LazFTPPathFieldInternal.this.transferFocus();
							SwingUtils.applyOrientation(menu);
							menu.showBelowMiddle(childs);
						}
					});

					contentPanel.add(childs);

					menu.addPopupMenuListener(new PopupMenuListener() {

						@Override
						public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {

						}

						@Override
						public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
							childs.setSelected(false);
						}

						@Override
						public void popupMenuCanceled(final PopupMenuEvent e) {
							childs.setSelected(false);
						}
					});

					first = false;
				}
			}

			// Filling space
			contentPanel.add(new JLabel());

			// Shortening long elemets
			if (!SystemUtils.isWindows()) {
				while (getRootsMenu().getComponentCount() > getRootsMenuItemsCount()) {
					getRootsMenu().remove(0);
				}
			}

			if (canShortenPath()) {
				getRootsMenu().addSeparator(0);
			}

			while (canShortenPath()) {

				// Andding menu element
				final WebButton wb = (WebButton) contentPanel.getComponent(2);
				final WebMenuItem menuItem = new WebMenuItem();
				menuItem.setIcon((Icon) wb.getClientProperty(FILE_ICON));
				menuItem.setText(wb.getText());
				menuItem.addActionListener(wb.getActionListeners()[0]);
				getRootsMenu().add(menuItem, 0);

				// Removing hidden path and menu buttons from panel
				contentPanel.remove(2);
				contentPanel.remove(2);
			}

			// Updating pane
			revalidate();
			repaint();
		}

		protected WebToggleButton getRootsArrowButton(final boolean ltr) {

			if (rootsArrowButton == null) {

				rootsMenu = new WebPopupMenu();

				final File[] rootFiles = getDiskRoots();

				for (final File root : FileUtils.sortFiles(rootFiles)) {

					final WebMenuItem menuItem = new WebMenuItem(FileUtils.getDisplayFileName(root));
					menuItem.setIcon(FileUtils.getFileIcon(FileUtils.getDiskRoots()[0], false));

					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(final ActionEvent e) {

							folderSelected(root);
						}
					});

					rootsMenu.add(menuItem);
					rootsMenuItemsCount++;
				}

				rootsArrowButton = new WebToggleButton();
				rootsArrowButton.setIcon(ltr ? right : left);
				rootsArrowButton.setSelectedIcon(down);
				rootsArrowButton.setShadeToggleIcon(false);
				rootsArrowButton.setRound(0);
				rootsArrowButton.setShadeWidth(0);
				rootsArrowButton.setRolloverDecoratedOnly(true);
				rootsArrowButton.setRolloverDarkBorderOnly(false);
				rootsArrowButton.setFocusable(false);
				rootsArrowButton.setMargin(0);
				rootsArrowButton.setLeftRightSpacing(0);
				rootsArrowButton.setComponentPopupMenu(rootsMenu);

				rootsArrowButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {

						LazFTPPathFieldInternal.this.transferFocus();
						SwingUtils.applyOrientation(rootsMenu);
						rootsMenu.showBelowMiddle(rootsArrowButton);
					}
				});

				rootsMenu.addPopupMenuListener(new PopupMenuListener() {

					@Override
					public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
					}

					@Override
					public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
						rootsArrowButton.setSelected(false);
					}

					@Override
					public void popupMenuCanceled(final PopupMenuEvent e) {
						rootsArrowButton.setSelected(false);
					}
				});
			}
			else {
				rootsArrowButton.setIcon(ltr ? right : left);
			}

			while (rootsMenu.getComponentCount() > rootsMenuItemsCount)
				rootsMenu.remove(0);

			return rootsArrowButton;
		}

		@Override
		public File[] getDiskRoots() {
			return new File[]{ new FTPFile("FTP:\\") };
		}

		private void disconnect() {

			if (manager != null)
				manager.close();

			manager = null;
			opts = null;
			btnConnect.setType(LazButtonType.CONNECT);
		}

		private void connect() {

			if (view == null) {
				view = new FTPConnectionView();
				view.initForm();
			}

			LazFormView.openForm(this, view, ftpConfig);
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			Object source = event.getSource();

			if (source == btnConnect) {

				if (btnConnect.getType() == LazButtonType.DISCONNECT)
					disconnect();
				else
					connect();
			}
		}

		@Override
		public void onSave(LazFormEvent event) {

			if (FTPConnectionView.class.isAssignableFrom(event.getSource().getClass())) {

				try {

					LazEvent
						.dispatchEvent(LazFTPPathFieldInternal.this, new LazPathFieldEvent(LazFTPPathFieldInternal.this, LazPathFieldEvent.CONNECTING));
					view.dispose();

					view = (FTPConnectionView) event.getSource();
					ftpConfig = view.getDTO();

					manager = new StandardFileSystemManager();
					manager.init();

					opts = new FileSystemOptions();
					SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
					SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
					SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

					new Thread() {
						public void run() {

							try {

								String sftpUri = ftpConfig.getBaseURL();
								FileObject remoteFolder = manager.resolveFile(sftpUri, opts);

								if (remoteFolder == null)
									throw new RuntimeException("Error while connecting to FTP");

								updatePath(getSelectedPath());

								btnConnect.setType(LazButtonType.DISCONNECT);
								LazEvent
									.dispatchEvent(LazFTPPathFieldInternal.this, new LazPathFieldEvent(LazFTPPathFieldInternal.this, LazPathFieldEvent.CONNECTED));
							}
							catch (Exception e) {

								if (manager != null)
									manager.close();

								manager = null;
								opts = null;

								LazAlert.showError(e.getMessage());
							}
						};
					}.start();
				}
				catch (Exception e) {
					LazAlert.showError(e.getMessage());
				}
			}
		}

		public FTPConfiguration getFtpConfig() {
			return ftpConfig;
		}

		public void setFtpConfig(FTPConfiguration ftpConfig) {
			this.ftpConfig = ftpConfig;
		}

		@Override
		public void onLoad(LazFormEvent event) {
		}

		@Override
		public void onBeforeSave(LazFormEvent event) throws BusinessException {
		}
	}
}