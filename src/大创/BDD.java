package ��;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JFrame;

class Map
{
	HashMap<String,String> map;
	public Map()
	{
		map = new HashMap<String,String>();
	}
}
class Bin 
{
	static Map maps[];
	int size;
	int row=0;
	public void createMap(ArrayList<String> domainSize)
	{
		maps = new Map[domainSize.size()];
		for(int i=0;i<domainSize.size();i++)
		{
			maps[i] = new Map();
		}
		int length;
		if(domainSize.size()!=0)
		{
			for(int i=0;i<domainSize.size();i++)
			{
				size=Integer.parseInt(domainSize.get(i));
				length=bite(size);//�ü�λ��������
				row=row+length;
				for(int j=0;j<size;j++)
				{
					Integer kk = new Integer(j);
					maps[i].map.put(kk.toString(), toBinary(j,length));
				}
			}
		}
	}
	public int bite(int size)
	{
		int x = 2;
		int count = 1;
		while(x<size)
		{
			x=x*2;
			count++;
		}
		return count;
	}
	public String toBinary(int a,int length)
	{
		String str;
		Stack<Integer> s = new Stack<Integer>();
		str="";
		if(a!=0)
		{
			while(a!=0)
			{
				s.push(a%2);
				a=a/2;
			}
			while(!s.empty())
			{
				str +=s.peek();
				s.pop();
			}
		}
		else
		{
			str="0";
		}
		if(str.length()<length) //���Ȳ���
		{
			String yy = "";
			for(int i=0;i<length-str.length();i++)
			{
				yy=yy+"0";
			}
			str=yy+str;
		}
		//System.out.println(str);
		return str;
	}
}
public class BDD {
	static float bdd_create_time=0;
	static float bdd_momery = 0;
	int count=0;
    int[][]solutions;
	int line;
	int row;	
	Bin bin;	
	int counts=0;
	Node root;
	Same_Instruction ins;
	ArrayList<Sets> sets;
	SortTheFile sourceFile;
	public BDD(){
		sourceFile=new SortTheFile();
		bin=new Bin();
		line=sourceFile.line;
		bin.createMap(sourceFile.domin);
		row=bin.row;
		solutions=new int[line][row];
		String temp="";
		int dec=0;
		int x=0;
		for(int i=0;i<sourceFile.line;i++){
			x=0;
			for(int j=0;j<sourceFile.row;j++){
				{
					dec=sourceFile.solutions[i][j];
					temp=bin.maps[j].map.get(String.valueOf(dec));
					for(int k=0;k<temp.length();k++){
						solutions[i][x]=Integer.parseInt(temp.charAt(k)+"");
						x++;
					}
				}
			}
		}
		/*for(int i=0;i<line;i++){
			for(int j=0;j<row;j++){
				System.out.print(" "+solutions[i][j]);
			}
			System.out.println("");
		}*/
		sets=new ArrayList<Sets>();
		for(int i=0;i<=line;i++){
			sets.add(new Sets());
		}
		ins=new Same_Instruction();
		root=new Node(0);
		root.num=count;
		Runtime runtime = Runtime.getRuntime();
		long start_m = runtime.freeMemory();
		long start = System.currentTimeMillis();
		BDDCreate();
		long end = System.currentTimeMillis();
		long end_m = runtime.freeMemory();
		bdd_create_time = (float) ((float)(end-start)/1000.0);
		bdd_momery = (float)((start_m-end_m)/(1024*1024));
		
		System.out.println("BDD����ʱ�� "+BDD.bdd_create_time+"  BDD��ռ�ڴ�   "+BDD.bdd_momery);
		System.out.println("�ڵ��� "+counts);
	}
	public void BDDCreate(){
		Node root=new Node(0);
		root.num=count;
		count++;
		Node link=root;
		Node temp=new Node(0);
		Node leftBranch=null,leftEnd=null,rightBranch=null,rightEnd=null;
		boolean newNode=true;
		//��ʼ����һ��p��
		for(int i=1;i<=row;i++){
			temp=new Node(i);
			temp.pre.add(link);
			link.rear.add(temp);
			temp.weight.add(solutions[0][i-1]);
			temp.num=count;
			count++;
			link=temp;
		}
		for(int i=2;i<=line;i++){
			//�ӵڶ��п�ʼ ÿһ�����μ�������
			link=root;
			newNode=true;
			for(int j=1;j<=row;j++){
				//���ͬһ�� ������ǰһ�е�Ȩֵ��ͬ �Ͳ��½��ڵ�			
				if(solutions[i-2][j-1]==solutions[i-1][j-1]&&newNode==true){
					link=link.rear.get(link.rear.size()-1);
					continue;
				}
				else{
					temp=new Node(j);
					temp.pre.add(link);
					link.rear.add(temp);
					temp.weight.add(solutions[i-1][j-1]);
					temp.num=count;
					count++;
					if(newNode==true){
						rightBranch=link;
						newNode=false;
					}
					if(j==row)
						rightEnd=temp;
					link=temp;
				}
			}
			if(i==2){
				leftBranch=rightBranch;
				leftEnd=leftBranch;
				while(leftEnd.rear.size()!=0)
					leftEnd=leftEnd.rear.get(0);
				//��ʼ��sets����� �ڵ�
				link=leftBranch;
				//sets.get(link.floor).set.add(link);
				link=link.rear.get(link.rear.size()-2);
				while(link.rear.size()!=0){
					sets.get(link.floor).set.add(link);
					link=link.rear.get(link.rear.size()-1);
				}
				sets.get(link.floor).set.add(link);
		     	leftBranch=rightBranch;
				leftEnd=rightEnd;
				continue;
			}
			ins.judge(rightBranch, leftEnd, sets);
			leftBranch=rightBranch;
			leftEnd=rightEnd;
			if(i==line){
				ins.judge(root, rightEnd, sets);
			}
		}
		for(int i=1;i<=line;i++)
			counts=counts+sets.get(i).set.size();
		System.out.println("daxiao "+counts);
	}
}
