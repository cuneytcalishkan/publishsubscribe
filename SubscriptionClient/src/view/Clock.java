/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 *
 * @author Natan
 */
public class Clock extends Component {

    protected DecimalFormat tflz, tf;
    protected boolean done = false;

    public Clock() {
        new Thread(new Runnable() {

            public void run() {
                while (!done) {
                    Clock.this.repaint(); // request a redraw
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) { /* do nothing */

                    }
                }
            }
        }).start();
        tf = new DecimalFormat("#0");
        tflz = new DecimalFormat("00");
    }

    public void stop() {
        done = true;
    }

    /* paint() - get current time and draw (centered) in Component. */
    @Override
    public void paint(Graphics g) {
        Calendar myCal = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        sb.append(tf.format(myCal.get(Calendar.HOUR_OF_DAY)));
        sb.append(':');
        sb.append(tflz.format(myCal.get(Calendar.MINUTE)));
        sb.append(':');
        sb.append(tflz.format(myCal.get(Calendar.SECOND)));
        String s = sb.toString();
        FontMetrics fm = getFontMetrics(getFont());
        int x = (getSize().width - fm.stringWidth(s)) / 2;
        // System.out.println("Size is " + getSize());
        g.drawString(s, x, 10);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 30);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 10);
    }
}
