
package  ��;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

import javax.swing.JFrame;
class SortTheFile {
	int line=0;
	int row=0;
	int [][]init;
	int [][]solutions;
	int []b;
	ArrayList<String> domin;
	public SortTheFile(){
		File file = new File("solution.txt"); //txt�ļ���ַ
	      try{
	    	  //���ļ�
	    	  FileReader fr=new FileReader(file);
	          BufferedReader br=new BufferedReader(fr);
	          //��ȡ����
	          Scanner sc=new Scanner(br.readLine());
	          line=sc.nextInt();
	          b=new int[line];
	          for(int i=0;i<line;i++){
	        	  b[i]=i;
	          }
	          //�������
	          String temp=br.readLine();
	          temp=temp.trim();
	          String[] sArray=temp.split(" ");
	          
	          row=sArray.length;
	          domin=new ArrayList<String>();
	          for(int i=0;i<row;i++){
	        	  domin.add(sArray[i]);
	          }
	          
	          //��ʼ������
	          init=new int[line][row];
	          solutions=new int[line][row];
	          int j=0;
	          while((temp=br.readLine())!=null){
	        	  temp=temp.trim();
	        	  sArray=temp.split(" ");
	        	  for(int i=0;i<row;i++){
	        		  init[j][i]=Integer.valueOf(sArray[i]);
		          }
	        	  j++;
	          }
	          br.close();
	          fr.close();    
	      }
	      catch(Exception e){
	    	  e.printStackTrace();
	      }
	     // Sort(0,line-1,0);		
		  for(int m=0;m<line;m++){
				for(int n=0;n<row;n++){
					solutions[m][n]=init[b[m]][n];
				//System.out.print("* "+solutions[m][n]);
				}
				//System.out.println("");
			}
	      
	}
	   void Sort(int m,int n,int r){
			if(m==n||r>=row)
				return;
			int temp=0;
			for(int i=m;i<=n;i++){
				for(int j=i+1;j<=n;j++){
					if(init[b[j]][r]<init[b[i]][r]){
						temp=b[j];
						b[j]=b[i];
						b[i]=temp;
					}
				}
			}
			temp=m-1;
			for(int i=m;i<n;i++){
				if(init[b[i]][r]==init[b[i+1]][r]&&i+1==n)
					Sort(temp+1,n,r+1);
				if(init[b[i]][r]==init[b[i+1]][r])
					continue;
				Sort(temp+1,i,r+1);
				temp=i;
			}
		}
}


class Node{
	int floor;// ��ǰ�����еĲ���
	ArrayList<Node> rear;
	int solution_num = 1;
	Node who=null; //���ҵ���ͬ�㣬�������ֵ
	int num;
	//�ýڵ�ָ�����һ��ڵ�
	ArrayList<Node> pre;//ǰ���ڵ�
	//int weight;//ǰ�����ָ��ýڵ�ıߵ�Ȩֵ
	ArrayList<Integer> weight;
	public Node(int f){
		floor=f;
		pre=null;
		num=-1;
		pre=new ArrayList<Node>();
		weight=new ArrayList<Integer>();
		rear=new ArrayList<Node>();
	}
	public int hashCode() //��дhashCode
	{
			int value=1;
			//ʹ�õ�ǰ���ĳ��ߵ�Ȩֵ����ָ��Ľڵ�ı�żӺͲ���
			
			for(int i=0;i<rear.size();i++)
			{
				if(rear.get(i).weight.size()==0)
					continue;
				if(rear.get(i)!=null)
				{
					value = value+getWeight(this,i)+this.rear.get(i).num;
				}
			}
			return value;
	}
	public boolean equals(Object o)
	{
			Node e = (Node) o;
			if(e.rear.size()!=this.rear.size()) //��������ͬ
			{
				return false;
			}
			for(int i=0;i<this.rear.size();i++) //�鿴��Ӧ�ıߵ�Ȩֵ��ָ��Ľڵ��Ƿ���ͬ
			{				
				if(getWeight(e,i)!=getWeight(this,i))
				{

					return false;
				}
				if(this.rear.get(i).num!=e.rear.get(i).num)
				{
					return false;
				}
			}
			who=e;
			return true;
	}
	public int printTree(Node root){
		int count=0;
		LinkedList<Node> queue=new LinkedList<Node>();
		Node temp=root;
		int size=0;
		int f=root.floor;
		queue.add(root);
		//System.out.print("��"+root.floor+"�㣺");
		while(queue.size()!=0){
			queue=removeSame(queue);
			temp=queue.poll();
			count++;
			if(temp.floor-f==1){
		//		System.out.print("\n��"+temp.floor+"�㣺");
				f++;
			}
			System.out.print(" "+temp.num);
			for(int i=0;i<temp.rear.size();i++){
				queue.add(temp.rear.get(i));
			}
			
		}
		return count;
	}
	
	public ArrayList<ArrayList<Integer>> dynamicTree(Node root,int line,int row){
		LinkedList<Node> queue=new LinkedList<Node>();
		
		Node temp=root;
		int size=0;
		int f=root.floor;
		ArrayList<Node> already=new ArrayList<Node>();
		ArrayList<ArrayList<Integer>> able=new ArrayList<ArrayList<Integer>>();
		int x=0;
		queue.add(root);
		already.add(root);
	//	System.out.print("��"+root.floor+"�㣺");
		while(queue.size()!=0){
			queue=removeSame(queue);
			temp=queue.poll();
			if(temp.floor-f==1){
		//		System.out.print("\n��"+temp.floor+"�㣺");
				able.add(new ArrayList<Integer>());
				f++;
				x++;
			}
			if(temp.floor!=root.floor){
				for(int i=0;i<temp.pre.size();i++)
				{
					if(already.contains(temp.pre.get(i))){
						able.get(x-1).add(temp.weight.get(i));
					}
				}
			}	
		//	System.out.print(" "+temp.num);
			for(int i=0;i<temp.rear.size();i++){
				queue.add(temp.rear.get(i));
				already.add(temp.rear.get(i));
			}
			
		}
		//System.out.print("\n");
		for(int i=0;i<able.size();i++)
			removeDuplicate(able.get(i));
		//System.out.print("\n");
		return able;
	}
	public void removeDuplicate(ArrayList<Integer> list) {
		   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
		     for ( int j = list.size() - 1 ; j > i; j -- ) {
		       if (list.get(j).equals(list.get(i))) {
		         list.remove(j);
		       } 
		      } 
		   } 
		   System.out.println(list);
	} 
	public LinkedList<Node> removeSame(LinkedList<Node> queue){
		LinkedList<Node> diff=new LinkedList<Node>();
		diff.add(queue.peek());
		Node temp=new Node(0);
		boolean add=true;
		for(int i=0;i<queue.size();i++){
			temp=queue.get(i);
			add=true;
			for(int j=0;j<diff.size();j++){
				if(temp.num==diff.get(j).num){
					add=false;
					break;
				}
			}
			if(add==true)
				diff.add(temp);
		}
		return diff;
	}

	static int find(ArrayList<Node> list,Node node)
	{
		int i=0;
		if(list.size()>0)
		{
			while(i<list.size())
			{
				if(list.get(i)==node)
				{
					return i;
				}
				i++;
			}
			return -1; //�Ҳ�������-1
		}
		else
		{
			return -1; //�Ҳ�������-1
		}
	}
	int getWeight(Node node,int i)
	{
		int abc = find(node.rear.get(i).pre,node);
		if(abc!=-1)
		{
			return(node.rear.get(i).weight.get(abc));
		}
		else
		{
			return -1;
		}
	}
}



class Sets
{
	Set<Node> set ;
	public Sets()
	{
		set = new HashSet<Node>();
	}
}
class Same_Instruction 
{
	int count=0;
	Node curr = null; //��ǰ���Ľڵ�
	void judge(Node branch,Node leaf,ArrayList<Sets> sets)
	{
		curr = leaf;
		
		while(curr!=branch)
		{
			//�ж��Ƿ�ͬ��
			if(sets.get(curr.floor)!=null&&sets.get(curr.floor).set.contains(curr)==true)
			{
				if(curr.who!=null)
				{
					if(curr.num==curr.who.num)
						count--;
					//System.out.println("�� "+curr.num+"�� ��"+"�� "+curr.who.num+"��ͬ��");
					count++;
				}
				int size=curr.pre.size();
				Node whos = curr.who;
				for(int i=0;i<size;i++)
				{
					//�޸�ǰ���ڵ��rear
					int index = Node.find(curr.pre.get(i).rear,curr);
					if(index!=-1)
					{						
						curr.pre.get(i).rear.remove(index);
						curr.pre.get(i).rear.add(index,whos);
					}
					whos.pre.add(curr.pre.get(i));
					whos.weight.add(curr.weight.get(i));
				}
			}
			else //��ͬ��
			{
				sets.get(curr.floor).set.add(curr);
			}
			curr=curr.pre.get(curr.pre.size()-1);
			
		}
	}
}

class MDD{
	static float mdd_create_time=0;
	static float mdd_momery = 0;
	int count=0;
    int[][]solutions;
	int line;
	int row;
	int counts=0;//�������MDD���еĽڵ�
	Node root;
	Same_Instruction ins;
	ArrayList<Sets> sets;
	SortTheFile sourceFile;
	//Selection s;
	public MDD(){
		sourceFile=new SortTheFile();
		solutions=sourceFile.solutions;
		line=sourceFile.line;
		row=sourceFile.row;
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
		MDDCreate();
		long end = System.currentTimeMillis();
		long end_m = runtime.freeMemory();
		//root.dynamicTree(root,line,row);
		mdd_create_time = (float) ((float)(end-start)/1000.0);
		mdd_momery = (float)((start_m-end_m)/(1024*1024));
		
		System.out.println("MDD����ʱ�� "+MDD.mdd_create_time+"  ����ռ���ڴ� "+MDD.mdd_momery);
	
		System.out.println("�ڵ��� "+counts);
	}
	public void MDDCreate(){
		root=new Node(0);
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
	}
}

