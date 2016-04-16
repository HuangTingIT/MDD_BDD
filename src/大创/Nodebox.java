package ´ó´´;

import java.io.PrintWriter;


public class Nodebox 
{
	int key;
	Nodebox son=null;
	Nodebox brother=null;
	int value=-1;
	
	int add(int[] in)
	{
		int n=in.length;
		
		Nodebox p=this;
		Nodebox q=null;
		Nodebox qq=null;
		for(int i=0;i<n;i++)
		{
			q=p.son;
			int a=in[i];
			qq=null;
			while(q!=null&&q.key<a)
			{
				qq=q;
				q=q.brother;
			}
			
			if(q==null||q.key>a)
			{
				if(qq==null)
				{
					q=new Nodebox();
					q.brother=p.son;
					p.son=q;
					q.key=a;
				}
				else
				{
					q=new Nodebox();
					q.brother=qq.brother;
					qq.brother=q;
					q.key=a;
				}
			}
			p=q;
		}
		
		if(p.value==-1)
		{
			value++;
			p.value=value;
		}
		
		return p.value;
	}
	
	boolean isIn(int[] in)
	{
		int n=in.length;
		
		Nodebox p=this;
		Nodebox q=null;
		
		for(int i=0;i<n;i++)
		{
			q=p.son;
			int a=in[i];
			while(q!=null&&q.key!=a)
			{
				q=q.brother;
			}
			if(q==null)
			{
				return false;
			}
			p=q;
		}
		return true;
	}

	int getValue(int [] in)
	{
		int n=in.length;
		
		Nodebox p=this;
		Nodebox q=null;
		
		for(int i=0;i<n;i++)
		{
			q=p.son;
			int a=in[i];
			while(q!=null&&q.key!=a)
			{
				q=q.brother;
			}
			if(q==null)
			{
				return -1;
			}
			p=q;
		}
		return p.value;
	}

	void gettuple(int n, int soln, PrintWriter pw)
	{
		int t[]=new int[n];
		
		int sn=0;
		
		Nodebox stack[]=new Nodebox[n+1];
		
		int up=1;
		
		stack[0]=son;
		
		int tn=0;
		
		
		if(soln==0)
		{
			System.out.println("none");
			return ;
		}
		
		while(up!=0)
		{
			Nodebox p=stack[up-1];
			
			if(p!=null)
			{
				
				t[tn]= p.key;
				tn++;
				if(p.son!=null)
				{
				   stack[up]=p.son;
				   up++;
				}
				else
				{
					tn--;
					up--;
					while(p.brother==null)
					{
						tn--;
						if(up==0)
						{
			            	String s="";
							for(int i=0;i<n;i++)
							{
			            		 s+=t[i];
			            		 if(i==n-1)
			            		 {
			            			 break;
			            		 }
			            		 s+=" ";
							}
			                pw.write(""+s);
			                pw.write("\r\n");
			                
							if(soln-1==sn)
							{
								System.out.println("ok");
							}
							return;
						}						
						p=stack[up-1];
						up--;
					}
					
					p=p.brother;
					stack[up]=p;
					up++;
					
					sn++;
					
	            	String s="";
					for(int i=0;i<n;i++)
					{
	            		 s+=t[i];
	            		 if(i==n-1)
	            		 {
	            			 break;
	            		 }
	            		 s+=" ";
					}
	                pw.write(""+s);
	                pw.write("\r\n");
				}
			}
			
		}
		

		return;
	}

}
