package CardGame;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * <pre>
 * File         FindSolution.java
 * Project      24 Points Card Game
 * Description  A class representing a GUI to find a solution for 4 inputs
 * Platform     jdk 1.8.0_77; NetBeans IDE 8.2; Mac Os X
 * Course       CS 143, SCC
 * Hours        2 hour
 * Created on   Feb 20 2017
 </pre>
 *
 * @author:	siyu.pan
 */
public class FindSolution extends javax.swing.JDialog {
    
    /**
     * external file name pf the solutions.
     */
    private final String fileName = "src/CardGame/Solution.txt";
    
    /**
     * Creates new form FindSolution.
     * @param parent the parent form
     * @param modal modality
     * @param cards the 4 crads drawn
     */
    public FindSolution(java.awt.Frame parent, boolean modal, Card[] cards) {
        super(parent, modal);
        try {
            initComponents();
            //set buttonAdd as default
            this.getRootPane().setDefaultButton(solutionJButton);
            //centers the form at start.
            this.setLocationRelativeTo(null);
            //set icon
            this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/CardGame/Poker/Heart1.jpg"));
            
            number1JTextField.requestFocus();
            this.setModal(true);
            this.setAlwaysOnTop(true);
                 
            //displayer in the 4 values of the cards drawn
            displayNumbers(cards);
        } catch (IOException ex) {
            Logger.getLogger(FindSolution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Display the values of the 4 cards in the textFields.
     * @param cards the 4 crads drawn
     * @throws java.io.IOException
     * @see Card
     */
    public void displayNumbers(Card[] cards) throws IOException
    {
        try 
        {
            //create an arrayList of the values of 4 cards
            ArrayList<Integer> numbers = new ArrayList<>();
            for(int i = 0; i < 4; i++)
            {          
                numbers.add(cards[i].getValue());
            }
            
            //diaplay the 4 values in the 4 textFields
            number1JTextField.setText(String.valueOf(numbers.get(0)));
            number2JTextField.setText(String.valueOf(numbers.get(1)));
            number3JTextField.setText(String.valueOf(numbers.get(2)));
            number4JTextField.setText(String.valueOf(numbers.get(3)));
            
            solutionJTextField.setText(findSolution(numbers));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FindSolution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Display the values of the 4 cards in the textFields.
     * @param numbers An ArrayList of the values of the 
     * @return A string of the solution. "No solution" if the values doesn't have a solution.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @see Card
     */
    public String findSolution(ArrayList<Integer> numbers) throws FileNotFoundException, IOException
    {
        String solution = "No Solution.";
        try
        {
            FileReader inputFile = new FileReader(fileName);
            BufferedReader input = new BufferedReader(inputFile);
            //read the first line
            String line = input.readLine();
            ArrayList<Integer> inputs = new ArrayList<>();
            while(line != null)
            {
                StringTokenizer token = new StringTokenizer(line, " ");
                for(int i = 0; i < 4; i++)
                {
                    String s = token.nextToken();
                    if(s != null && s != " ")
                        inputs.add(Integer.parseInt(s));
                }
                for(int i = 0; i < 4; i++)
                {
                    for(int j = 0; j < inputs.size(); j++)
                    {
                        //if the two numbers are equal, remove the number from the cardNumbers ArrayList
                        if(numbers.get(i) == inputs.get(j))
                        {
                            inputs.remove(j);
                            break;
                        }
                    }
                }
                //the four numebrs are valid only is all the numbers in the cardNumbers have a match
                if(inputs.isEmpty())
                {
                    solution = "";
                    while(token.hasMoreTokens())
                        solution = solution + token.nextToken();
                    break;
                }
                line = input.readLine();
                inputs.clear();
            }
            input.close();
        }
        catch(FileNotFoundException exp)
        {
            JOptionPane.showMessageDialog(null, fileName + " doesnot exist", "File Input Error", JOptionPane.WARNING_MESSAGE);
            //add JFileChooser to find the file
        }
        
        catch(IOException exp)
        {
            JOptionPane.showMessageDialog(null, fileName + " is corrupt", "File Input Error", JOptionPane.WARNING_MESSAGE);
        }
        finally{
            return solution;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleJPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        middleJPane = new javax.swing.JPanel();
        number1JTextField = new javax.swing.JTextField();
        number2JTextField = new javax.swing.JTextField();
        number3JTextField = new javax.swing.JTextField();
        number4JTextField = new javax.swing.JTextField();
        bottomJPane = new javax.swing.JPanel();
        solutionJTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        solutionJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        titleJPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Malayalam MN", 0, 24)); // NOI18N
        jLabel2.setText("Find Solution");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CardGame/title.jpg"))); // NOI18N
        jLabel5.setText("jLabel3");

        javax.swing.GroupLayout titleJPaneLayout = new javax.swing.GroupLayout(titleJPane);
        titleJPane.setLayout(titleJPaneLayout);
        titleJPaneLayout.setHorizontalGroup(
            titleJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titleJPaneLayout.createSequentialGroup()
                .addContainerGap(376, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(19, 19, 19))
            .addGroup(titleJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(titleJPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(182, Short.MAX_VALUE)))
        );
        titleJPaneLayout.setVerticalGroup(
            titleJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titleJPaneLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(21, 21, 21))
            .addGroup(titleJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(titleJPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        middleJPane.setLayout(new java.awt.GridLayout(1, 4, 5, 5));

        number1JTextField.setFont(new java.awt.Font("Lucida Grande", 0, 72)); // NOI18N
        number1JTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        number1JTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                number1JTextFieldActionPerformed(evt);
            }
        });
        middleJPane.add(number1JTextField);

        number2JTextField.setFont(new java.awt.Font("Lucida Grande", 0, 72)); // NOI18N
        number2JTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        number2JTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                number2JTextFieldActionPerformed(evt);
            }
        });
        middleJPane.add(number2JTextField);

        number3JTextField.setFont(new java.awt.Font("Lucida Grande", 0, 72)); // NOI18N
        number3JTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        number3JTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                number3JTextFieldActionPerformed(evt);
            }
        });
        middleJPane.add(number3JTextField);

        number4JTextField.setFont(new java.awt.Font("Lucida Grande", 0, 72)); // NOI18N
        number4JTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        number4JTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                number4JTextFieldActionPerformed(evt);
            }
        });
        middleJPane.add(number4JTextField);

        solutionJTextField.setEditable(false);

        jLabel1.setText("Solution:");

        solutionJButton.setText("Find Solution");
        solutionJButton.setToolTipText("Find the correct solution for the 4 numbers above");
        solutionJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solutionJButtonActionPerformed(evt);
            }
        });

        cancelJButton.setText("Cancel");
        cancelJButton.setToolTipText("Return to main form");
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottomJPaneLayout = new javax.swing.GroupLayout(bottomJPane);
        bottomJPane.setLayout(bottomJPaneLayout);
        bottomJPaneLayout.setHorizontalGroup(
            bottomJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomJPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(solutionJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(solutionJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelJButton)
                .addContainerGap())
        );
        bottomJPaneLayout.setVerticalGroup(
            bottomJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomJPaneLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(bottomJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(solutionJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(solutionJButton)
                    .addComponent(cancelJButton))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(titleJPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(middleJPane, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(bottomJPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 351, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(titleJPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(266, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(104, Short.MAX_VALUE)
                    .addComponent(middleJPane, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(79, 79, 79)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(273, Short.MAX_VALUE)
                    .addComponent(bottomJPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void number1JTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_number1JTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_number1JTextFieldActionPerformed

    private void number2JTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_number2JTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_number2JTextFieldActionPerformed

    private void number3JTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_number3JTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_number3JTextFieldActionPerformed

    private void number4JTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_number4JTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_number4JTextFieldActionPerformed

    private void solutionJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solutionJButtonActionPerformed
        try {
            ArrayList<Integer> numbers = new ArrayList<>();
            numbers.add(Integer.parseInt(number1JTextField.getText()));
            numbers.add(Integer.parseInt(number2JTextField.getText()));
            numbers.add(Integer.parseInt(number3JTextField.getText()));
            numbers.add(Integer.parseInt(number4JTextField.getText()));
            solutionJTextField.setText(findSolution(numbers));
        } catch (IOException ex) {
            Logger.getLogger(FindSolution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_solutionJButtonActionPerformed

    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
        //close the form
        this.dispose();
    }//GEN-LAST:event_cancelJButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FindSolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FindSolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FindSolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FindSolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FindSolution dialog = new FindSolution(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomJPane;
    private javax.swing.JButton cancelJButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel middleJPane;
    private javax.swing.JTextField number1JTextField;
    private javax.swing.JTextField number2JTextField;
    private javax.swing.JTextField number3JTextField;
    private javax.swing.JTextField number4JTextField;
    private javax.swing.JButton solutionJButton;
    private javax.swing.JTextField solutionJTextField;
    private javax.swing.JPanel titleJPane;
    // End of variables declaration//GEN-END:variables
}
