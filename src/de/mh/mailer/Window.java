package de.mh.mailer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Window extends JFrame {

	private static final long serialVersionUID = 3613061522237715452L;
	
	private JPanel contentPane;
	private JTextField textHost;
	private JTextField textFrom;
	private JTextField textSubject;
	private JTextField textUsername;
	private JTextField textPassword;
	private JProgressBar progressBar;
	private JButton buttonStart;
	private JButton buttonStop;
	private JSpinner numInterval;
	private JSpinner numAmount;
	private JSpinner numOffset;
	private Mailer mailer;
	private Timer timer;

	
	/**
	 * Create the frame.
	 */
	public Window(Mailer mailer) {
		
		this.mailer = mailer;
		setResizable(false);
		setTitle("Mailer (© 2015 Micha Hanselmann)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHost = new JLabel("Host");
		lblHost.setBounds(6, 12, 61, 16);
		contentPane.add(lblHost);
		
		textHost = new JTextField();
		textHost.setBounds(111, 6, 365, 28);
		contentPane.add(textHost);
		textHost.setColumns(10);
		
		textFrom = new JTextField();
		textFrom.setBounds(111, 126, 365, 28);
		contentPane.add(textFrom);
		textFrom.setColumns(10);
		
		JLabel lblVon = new JLabel("From");
		lblVon.setBounds(6, 132, 61, 16);
		contentPane.add(lblVon);
		
		textSubject = new JTextField();
		textSubject.setBounds(111, 166, 365, 28);
		contentPane.add(textSubject);
		textSubject.setColumns(10);
		
		JLabel lblBetreff = new JLabel("Subject");
		lblBetreff.setBounds(6, 172, 61, 16);
		contentPane.add(lblBetreff);
		
		textUsername = new JTextField();
		textUsername.setBounds(111, 46, 365, 28);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
		
		JLabel lblBenutzername = new JLabel("Username");
		lblBenutzername.setBounds(6, 52, 93, 16);
		contentPane.add(lblBenutzername);
		
		textPassword = new JTextField();
		textPassword.setBounds(111, 86, 365, 28);
		contentPane.add(textPassword);
		textPassword.setColumns(10);
		
		JLabel lblPasswort = new JLabel("Password");
		lblPasswort.setBounds(6, 92, 61, 16);
		contentPane.add(lblPasswort);
		
		JLabel lblAdresslisteUnbekannt = new JLabel("No recipients loaded");
		lblAdresslisteUnbekannt.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdresslisteUnbekannt.setBounds(16, 287, 158, 16);
		contentPane.add(lblAdresslisteUnbekannt);
		
		JLabel lblNachrichtUnbekannt = new JLabel("No message loaded");
		lblNachrichtUnbekannt.setHorizontalAlignment(SwingConstants.CENTER);
		lblNachrichtUnbekannt.setBounds(197, 287, 159, 16);
		contentPane.add(lblNachrichtUnbekannt);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(6, 287, 466, 20);
		progressBar.setVisible(false);
		progressBar.setStringPainted(true);
		contentPane.add(progressBar);
		
		JButton buttonLoadRecipients = new JButton("Load recipients list");
		buttonLoadRecipients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// open file dialog
				JFileChooser fc = new JFileChooser();
				if (fc.showOpenDialog(Window.this) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						mailer.recipients = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
						progressBar.setMaximum(mailer.recipients.size());
						progressBar.setString("0 / " + mailer.recipients.size());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(Window.this, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
						mailer.log(e1.toString());
					}
					lblAdresslisteUnbekannt.setText(file.getName());
					if (mailer.recipients != null && mailer.message != null) buttonStart.setEnabled(true);
				}
				
			}
		});
		buttonLoadRecipients.setBounds(6, 246, 178, 29);
		contentPane.add(buttonLoadRecipients);
		
		JButton buttonLoadMessage = new JButton("Load message");
		buttonLoadMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// open file dialog
				JFileChooser fc = new JFileChooser();
				if (fc.showOpenDialog(Window.this) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						mailer.message = new String(Files.readAllBytes(file.toPath()), StandardCharsets.ISO_8859_1);
						// TODO: better style!
						/*
						// load attachments
						for (int i = 0; i < mailer.message.length() - 5; i++) {
							if (mailer.message.substring(i, i + 5).equals("src=\"")) {
								
								// get URL
								String url = "";
								for (i = i + 5; mailer.message.charAt(i) != '"'; i++) {
									url += mailer.message.charAt(i);
								}
								
								// add attachment
								File attachment = new File(file.getParent() + "/" + url);
								if (Files.exists(attachment.toPath())) {
									if (!mailer.attachments.contains(attachment)) mailer.attachments.add(attachment);
								} else {
									System.out.println("Attachment not found: " + attachment.getAbsolutePath());
									mailer.log("Attachment not found: " + attachment.getAbsolutePath());
								}
								
							}
						}
						
						//for (File f : mailer.attachments) System.out.println(f.getAbsolutePath());
						
						// correct image URLs
						mailer.message = mailer.message.replace("src=\"", "src=\"cid:");
						*/
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(Window.this, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
						mailer.log(e1.toString());
					}
					lblNachrichtUnbekannt.setText(file.getName());
					if (mailer.recipients != null && mailer.message != null) buttonStart.setEnabled(true);
				}
				
			}
		});
		buttonLoadMessage.setBounds(187, 246, 178, 29);
		contentPane.add(buttonLoadMessage);
		
		numInterval = new JSpinner();
		numInterval.setBounds(97, 206, 47, 28);
		numInterval.setModel(new SpinnerNumberModel(1, 1, 60, 1));
		contentPane.add(numInterval);
		
		JLabel lblIntervallmin = new JLabel("Interval (min)");
		lblIntervallmin.setBounds(6, 212, 93, 16);
		contentPane.add(lblIntervallmin);
		
		numAmount = new JSpinner();
		numAmount.setBounds(411, 206, 61, 28);
		numAmount.setModel(new SpinnerNumberModel(1, 1, null, 1));
		contentPane.add(numAmount);
		
		numOffset = new JSpinner();
		numOffset.setBounds(205, 206, 73, 28);
		numOffset.setModel(new SpinnerNumberModel(0, 0, null, 1));
		contentPane.add(numOffset);
		
		JLabel lblMailsProIntervall = new JLabel("Mails per interval");
		lblMailsProIntervall.setBounds(294, 212, 117, 16);
		contentPane.add(lblMailsProIntervall);
		
		buttonStart = new JButton("Start");
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// check inputs
				String errors = "";
				if (textHost.getText().length() == 0) errors += "No host entered\n";
				if (textUsername.getText().length() == 0) errors += "No username entered\n";
				if (textPassword.getText().length() == 0) errors += "No password entered\n";
				if (textFrom.getText().length() == 0) errors += "No from entered\n";
				if (textSubject.getText().length() == 0) errors += "No subject entered\n";
				if ((int)numOffset.getValue() >= mailer.recipients.size()) errors += "Offset too high";
				if (errors.length() > 0) {
					JOptionPane.showMessageDialog(Window.this, errors, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// update GUI
				lblAdresslisteUnbekannt.setVisible(false);
				lblNachrichtUnbekannt.setVisible(false);
				progressBar.setVisible(true);
				textHost.setEnabled(false);
				textUsername.setEnabled(false);
				textPassword.setEnabled(false);
				textFrom.setEnabled(false);
				textSubject.setEnabled(false);
				numInterval.setEnabled(false);
				numAmount.setEnabled(false);
				numOffset.setEnabled(false);
				buttonLoadMessage.setEnabled(false);
				buttonLoadRecipients.setEnabled(false);
				buttonStart.setVisible(false);
				buttonStop.setVisible(true);
				
				// save temp
				try {
					PrintWriter writer = new PrintWriter(".mailer");
					writer.println(textHost.getText());
					writer.println(textUsername.getText());
					writer.println(textPassword.getText());
					writer.println(textFrom.getText());
					writer.println(textSubject.getText());
					writer.println(numInterval.getValue());
					writer.println(numAmount.getValue());
					writer.close();
				} catch (Exception e1) {
					mailer.log(e1.toString());
				}
				
				// start
				mailer.log("Started (offset: " + (int)numOffset.getValue() + ")");
				start();
				
			}
		});
		buttonStart.setEnabled(false);
		buttonStart.setBounds(367, 246, 111, 29);
		contentPane.add(buttonStart);
		
		buttonStop = new JButton("Stop");
		buttonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// stop timer
				timer.stop();
			
				// update GUI
				lblAdresslisteUnbekannt.setVisible(true);
				lblNachrichtUnbekannt.setVisible(true);
				progressBar.setVisible(false);
				textHost.setEnabled(true);
				textUsername.setEnabled(true);
				textPassword.setEnabled(true);
				textFrom.setEnabled(true);
				textSubject.setEnabled(true);
				numInterval.setEnabled(true);
				numAmount.setEnabled(true);
				numOffset.setEnabled(true);
				buttonLoadMessage.setEnabled(true);
				buttonLoadRecipients.setEnabled(true);
				buttonStart.setVisible(true);
				buttonStop.setVisible(false);
				
				// reset
				mailer.log("Stopped (index: " + mailer.index + ")");
				mailer.index = 0;
				progressBar.setValue(0);
				progressBar.setString("0 / " + mailer.recipients.size());
				
			}
		});
		buttonStop.setBounds(367, 246, 111, 29);
		buttonStop.setVisible(false);
		contentPane.add(buttonStop);
		
		JLabel lblOffset = new JLabel("Offset");
		lblOffset.setBounds(162, 212, 39, 16);
		contentPane.add(lblOffset);
		
		// restore temp if exists
		File temp = new File(".mailer");
		if (Files.exists(temp.toPath())) {
			try {
				List<String> lines = Files.readAllLines(temp.toPath());
				textHost.setText(lines.get(0));
				textUsername.setText(lines.get(1));
				textPassword.setText(lines.get(2));
				textFrom.setText(lines.get(3));
				textSubject.setText(lines.get(4));
				numInterval.setValue(Integer.valueOf(lines.get(5)));
				numAmount.setValue(Integer.valueOf(lines.get(6)));
			} catch (Exception e1) {
				mailer.log(e1.toString());
			}
		}
		
	}
	
	private void start() {
		
		// set values
		mailer.host = textHost.getText();
		mailer.user = textUsername.getText();
		mailer.password = textPassword.getText();
		mailer.from = textFrom.getText();
		mailer.subject = textSubject.getText();
		// (offset)
		mailer.index = (int)numOffset.getValue();
		progressBar.setValue(mailer.index);
		progressBar.setString(mailer.index + " / " + mailer.recipients.size());
		
		// setup timer
		timer = new Timer((int)numInterval.getValue() * 1000 * 60, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// send mails
				for (int i = 0; i < (int)numAmount.getValue(); i++) {
					
					try {
						mailer.send();
						mailer.index++;
						progressBar.setValue(mailer.index);
						progressBar.setString(mailer.index + " / " + mailer.recipients.size());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(Window.this, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
						mailer.log(e1.toString());
						numOffset.setValue(mailer.index + 1);
						mailer.log("An error occurred");
						buttonStop.doClick();
						break;
					}
					
					// all mails sent -> stop timer
					if (mailer.index >= mailer.recipients.size()) {
						mailer.log("All messages delivered successfully");
						buttonStop.doClick();
						JOptionPane.showMessageDialog(Window.this, "All messages delivered successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
					
				}
				
			}
			
		});
		
		// start timer
		timer.start();
		
	}
}
