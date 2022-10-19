
public class Calc
{
    private int rank;   //题目的难度等级: 有1、2、3三个可选项
    private int[] numAndop; //存储题目的操作数和操作符

    public Calc(int rank)
    {
        this.rank = rank;
    }

    //根据难度随机产生题目
    public int[] NumberSet()
    {
        int rand = 0;
        int[] result = new int[3];  //数组的前两个元素为操作数，第三个元素为操作符：0表示加法，1表示减法
        if (this.rank == 1)
        {
            rand = (int) (Math.random() * 10);
            result[0] = rand;
            rand = (int) (Math.random() * 10);
            result[1] = rand;
        }
        else if (this.rank == 2)
        {
            rand = (int) (Math.random() * 10) * 10;
            result[0] = rand;
            rand = (int) (Math.random() * 100);
            result[1] = rand;
        }
        else if (this.rank == 3)
        {
            rand = (int) (Math.random() * 100);
            result[0] = rand;
            rand = (int) (Math.random() * 100);
            result[1] = rand;
        }
        if ((Math.random() )<0.5)
            result[2] = 0;
        else
        {
            result[2] = 1;
            if (result[0]<result[1])    //减法的被减数要大于减数
            {
                int tmp = result[0];
                result[0] = result[1];
                result[1] = tmp;
            }
        }
        this.numAndop = new int[3];
        this.numAndop[0] = result[0];
        this.numAndop[1] = result[1];
        this.numAndop[2] = result[2];
        return result;
    }

    //检验结果正误
    public Boolean checkSum(int sum)
    {
        if (this.numAndop[2] == 0)
        {
            return sum == numAndop[0] + numAndop[1];
        }
        else
        {
            return sum == numAndop[0] - numAndop[1];
        }
    }

}
