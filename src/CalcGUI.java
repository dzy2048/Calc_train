import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CalcGUI
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            CalcFrame frame = new CalcFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocation(800,400);
            frame.setResizable(false);      //不允许最大化
            frame.setVisible(true);
        });
    }
}
class CalcFrame extends JFrame
{
    private Calc calc = null;
    private int[] question = new int[3];    //用于存储题目
    private int count = 0;  //循环计数，用于更新输入结果的某一位
    private List<JLabel> questLabel = new ArrayList<>();    //显示题目的label数组
    private List<JLabel> input = new ArrayList<>(); //用于显示用户输入结果的label数组

    public CalcFrame()
    {
        //设置界面的大小
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setSize(screenWidth / 2, screenHeight / 2);

        //设置标题和图标
        setTitle("加减法练习软件");
        setIconImage(new ImageIcon("src/img.jpg").getImage());

        List<JButton> buttons = new ArrayList<>();
        int buttonWidth = screenWidth / 40;
        int buttonHeight = screenHeight / 40;
        int spacing = buttonWidth / 4;
        int init_x = buttonWidth;
        int init_y = screenHeight / 2 - buttonHeight * 2;

        //最底部盒子
        Box hBox = Box.createHorizontalBox();

        //垂直盒子，将界面分为左右两个区域
        Box vBox1 = Box.createVerticalBox();
        Box vBox2 = Box.createVerticalBox();

        //vBox1中的水平盒子
        Box v1_hBox1 = Box.createHorizontalBox();  //放置题目的第一个操作数
        Box v1_hBox2 = Box.createHorizontalBox();  //放置题目的操作符和第二个操作数
        Box v1_hBox3 = Box.createHorizontalBox();  //放置分隔的横线
        Box v1_hBox4 = Box.createHorizontalBox();  //放置用户输入的结果
        Box v1_hBox5 = Box.createHorizontalBox();  //放置数字输入按钮
        Box v1_hBox6 = Box.createHorizontalBox();   //放置评判按钮和跳题按钮

        //vBox2中的水平盒子
        Box v2_hBox1 = Box.createHorizontalBox();   //放置难度选择框
        Box v2_hBox2 = Box.createHorizontalBox();   //放置开始作答按钮
        Box v2_hBox3 = Box.createHorizontalBox();   //放置评判结果


        //显示题目
        Font font = new Font("黑体",Font.PLAIN,25);
        UIManager.put("Label.font",font);   //改变JLabel的字体大小
        JLabel num1 = new JLabel("000");
        JLabel num2 = new JLabel("000");
        JLabel op = new JLabel("+");
        JLabel line = new JLabel("-----------------");
        questLabel.add(num1);
        questLabel.add(num2);
        questLabel.add(op);

        v1_hBox1.add(Box.createRigidArea(new Dimension(20,0)));
        v1_hBox1.add(num1);
        v1_hBox2.add(Box.createRigidArea(new Dimension(16,0)));
        v1_hBox2.add(op);
        v1_hBox2.add(num2);
        v1_hBox3.add(line);

        //定义显示用户输入结果的label
        for (int i=0 ;i<3 ;i++)
        {
            JLabel label = new JLabel("0");
            input.add(label);
        }
        v1_hBox4.add(Box.createRigidArea(new Dimension(20,0)));
        for (int i=2 ;i>=0 ;i--)
        {
            v1_hBox4.add(input.get(i));
        }


        //显示0-10的输入按钮
        for (int i=0 ;i<10 ;i++)
        {
            JButton button = new JButton(String.valueOf(i));
            buttons.add(button);
            v1_hBox5.add(button);
            v1_hBox5.add(Box.createHorizontalStrut(1));

            //添加事件监听器
            NumAction numAction = new NumAction(i);
            button.addActionListener(numAction);
        }

        //添加评判按钮、跳过题目按钮和清空输入按钮
        JButton checkButton = new JButton("评判");
        JButton skipButton = new JButton("跳过该题");
        JButton clearButton = new JButton("清空输入");
        v1_hBox6.add(checkButton);
        v1_hBox6.add(Box.createHorizontalStrut(screenWidth/50));
        v1_hBox6.add(skipButton);
        v1_hBox6.add(Box.createHorizontalStrut(screenWidth/50));
        v1_hBox6.add(clearButton);

        //添加跳过题目按钮的监听器
        SkipAction skipAction = new SkipAction();
        skipButton.addActionListener(skipAction);

        //添加清空按钮的监听器
        ClearAction clearAction = new ClearAction();
        clearButton.addActionListener(clearAction);

        //添加难度选择框
        JLabel rankText = new JLabel("选择难度");
        JComboBox<Integer> comboBox = new JComboBox<>();
        comboBox.addItem(1);
        comboBox.addItem(2);
        comboBox.addItem(3);
        v2_hBox1.add(Box.createHorizontalStrut(screenWidth/20));
        v2_hBox1.add(rankText);
        v2_hBox1.add(Box.createHorizontalStrut(screenWidth/40));
        v2_hBox1.add(comboBox);
        v2_hBox1.add(Box.createHorizontalStrut(screenWidth/20));

        //开始答题按钮
        JButton start = new JButton("开始答题");
        StartAction startAction = new StartAction(comboBox);
        start.addActionListener(startAction);
        v2_hBox2.add(start);

        //添加评判结果
        JLabel checkStr = new JLabel("请认真作答");
        v2_hBox3.add(checkStr);

        //设置评判按钮的监听器
        CheckAction checkAction = new CheckAction(checkStr);
        checkButton.addActionListener(checkAction);

        vBox1.add(Box.createRigidArea(new Dimension(0,100)));
        vBox1.add(v1_hBox1);
        vBox1.add(v1_hBox2);
        vBox1.add(v1_hBox3);
        vBox1.add(v1_hBox4);
        vBox1.add(v1_hBox5);
        vBox1.add(v1_hBox6);
        vBox1.add(Box.createRigidArea(new Dimension(0,screenHeight/20)));

        vBox2.add(Box.createRigidArea(new Dimension(0,screenHeight/15)));
        vBox2.add(v2_hBox1);
        vBox2.add(Box.createRigidArea(new Dimension(0,screenHeight/10)));
        vBox2.add(Box.createVerticalStrut(screenHeight/15));
        vBox2.add(v2_hBox2);
        vBox2.add(Box.createRigidArea(new Dimension(0,screenHeight/10)));
        vBox2.add(v2_hBox3);
        vBox2.add(Box.createRigidArea(new Dimension(0,screenHeight/20)));

        hBox.add(Box.createRigidArea(new Dimension(80,0)));
        hBox.add(vBox1);
        hBox.add(Box.createRigidArea(new Dimension(50,0)));
        hBox.add(vBox2);
        setContentPane(hBox);
    }

    //更新题目显示区域
    public void Update()
    {
        questLabel.get(0).setText(String.valueOf(question[0]));
        questLabel.get(1).setText(String.valueOf(question[1]));
        if (question[2] == 0)
            questLabel.get(2).setText("+");
        else
            questLabel.get(2).setText("-");
    }

    //清空用户输入
    public void ClearInput()
    {
        for (int i=0 ;i<3 ;i++)
        {
            input.get(i).setText("0");
        }
        count = 0;
    }

    //数字输入按钮的监听器
    class NumAction extends AbstractAction
    {
        public NumAction(int num)
        {
            putValue("num",num);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            JLabel label = input.get(count%3);
            int num = (int) getValue("num");
            label.setText(String.valueOf(num));
            count++;
        }
    }

    //评判按钮的监听器
    class CheckAction extends AbstractAction
    {
        JLabel actLabel;

        public CheckAction(JLabel actLabel)
        {
            this.actLabel = actLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (calc == null)   //还未开始作答
                return;

            int base = 1;
            int sum = 0;

            for (int i=0 ;i<3 ;i++)
            {
                sum = sum + base * Integer.parseInt(input.get(i).getText());
                base = base * 10;
            }

            if (calc.checkSum(sum))
            {
                actLabel.setText("答对了！");
                question = calc.NumberSet();
                Update();
            }
            else
                actLabel.setText("答错了！");

            ClearInput();
        }
    }

    //开始作答按钮的监听器
    class StartAction extends AbstractAction
    {
        private JComboBox<Integer> comboBox;

        public StartAction(JComboBox<Integer> comboBox)
        {
            this.comboBox = comboBox;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            int rank = (int) comboBox.getSelectedItem();
            calc = new Calc(rank);
            question = calc.NumberSet();
            Update();
            ClearInput();
        }
    }

    //跳过按钮的监听器
    class SkipAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (calc == null)
                return;
            question = calc.NumberSet();
            Update();
            ClearInput();
        }
    }

    //清空按钮的监听器
    class ClearAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            ClearInput();
        }
    }
}
