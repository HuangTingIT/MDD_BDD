package 大创;
public class Nodebox1 
{
	int Nodetype =0;
	
	int key;
	Nodebox1 son=null;
	Nodebox1 brother=null;
	Nodebox1 brother1=null;
	
	//balance tree for each node
	boolean color=false;
	
	int value=-1;
	
	void getAllValue(int[][] in, int maxd)
	{
		Nodebox1 stack[]=new Nodebox1[maxd+1];
		int up=1;
		stack[0]=this;
		
		int stackmark[]=new int[maxd+1];
	
		
		while(up>0)
		{
			
			Nodebox1 now=stack[up-1];
			
			if(stackmark[up-1]==0)
			{
				stackmark[up-1]=1;
				int val=now.value;
				
				if(val!=-1)
				{
					in[val]=new int[up-1];
					
					for(int i=1;i<up;i++)
					{
						in[val][i-1]=stack[i].key;
					}
				}
				
				if(now.son!=null)
				{
					stack[up]=now.son;
					stackmark[up]=0;
					up++;
				}
				
				continue;
			}
			
			if(stackmark[up-1]==1)
			{
				if(now.brother==null)
				{
					up--;
					continue;
				}
				
				stackmark[up-1]=0;
				stack[up-1]=now.brother;
				
				continue;
			}
		}
	}
	
	
	int add(int[] in)
	{
		int n=in.length;
		
		Nodebox1 p=this;
		Nodebox1 q=null;
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
				q=new Nodebox1();
				q.brother=p.son;
				p.son=q;
				q.key=a;
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
	
	int MDDadd(int[] in, int number, Nodebox1 path[])          // 采用红黑树建立索引     path can record the path from root to leaf for each tree in the node 
	{
		int n=in.length;
		
		Nodebox1 p=this;
		Nodebox1 q=null;
		Nodebox1 father=this;
		int pathn=0;
		Nodebox1 other=null;
		
		for(int i=0;i<n;i++)
		{
			path[0]=father;
			pathn=0;
			
			p=father;
			q=p.son;
			int a=in[i];
						
			while(q!=null&&q.key!=a)
			{
				if(q.key<a)
				{
					p=q;
					q=q.brother1;
					
					pathn++;
					path[pathn]=p;
					continue;
				}
				
				p=q;
				q=q.brother;
				pathn++;
				path[pathn]=p;
			}
			
			if(q==null)
			{
				q=new Nodebox1();
				q.key=a;
				
				if(p!=father)
				{
					if(p.key<a)
					{
						p.brother1=q;
					}
					else
					{
						p.brother=q;
					}
					
					father=q;
					
					
					int cur=pathn;
					Nodebox1 curnode=q;
					
					while(!path[cur].color)
					{
						if(cur==1)
						{
							path[cur].color=true;
							break;
						}
						
						Nodebox1 parent = path[cur];
						
						Nodebox1 parentparent=path[cur-1];
						
						boolean f1=false;   //left
						boolean f2=false;   //left
						
						
						if(parent.key<parentparent.key)
						{
							 other=parentparent.brother1;
							 f1=true;
						}
						else
						{
							other=parentparent.brother;
						}
						
						
						if(other!=null&&!other.color )
						{
							parent.color=true;
							other.color=true;
							parentparent.color=false;
							
							if(cur==2)
							{
								break;
							}
							
							curnode=parentparent;
							
							cur-=2;
							
							continue;
						}
						
					
						if(curnode.key>parent.key)
						{
							f2=true;
						}
						
						if(f1!=f2)
						{
							if(cur==2)
							{
								path[0].son=parent;
							}
							else
							{
								if(path[cur-2].key<parentparent.key)
								{
									path[cur-2].brother1=parent;
								}
								else
								{
									path[cur-2].brother=parent;
								}

							}
							
							if(f1)
							{
								parentparent.brother=parent.brother1;
								
								parent.brother1=parentparent;
								
								parent.color=true;
								
								parentparent.color=false;								
								
								break;
							}
							
							parentparent.brother1=parent.brother;
							
							parent.brother=parentparent;
							
							parent.color=true;
							
							parentparent.color=false;
							
							break;
						}
						
						
						//if(cur==1)System.out.println("ssssssssssssss");
						
						if(f2)
						{
							parent.brother1=curnode.brother;
							curnode.brother=parent;
							parentparent.brother=curnode;
							path[cur]=curnode;		
							curnode=parent;
							continue;
						}
						else
						{
							parent.brother=curnode.brother1;
							curnode.brother1=parent;
							parentparent.brother1=curnode;
							path[cur]=curnode;		
							curnode=parent;
							continue;
						}
						
					}
					
					continue;
				}
				
				q.color=true;
				p.son=q;
			}
			
			
			father=q;
		}
		
		if(father.value==-1)
		{
			value++;
			father.value=number;
			return -1;
		}

		return father.value;
	}
	
	boolean isIn(int[] in)
	{
		int n=in.length;
		
		Nodebox1 p=this;
		Nodebox1 q=null;
		
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
		
		Nodebox1 p=this;
		Nodebox1 q=null;
		
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

	int getNodenum()
	{
		int num=0;
		
		Nodebox1 p=this.son;
		
		while(p!=null)
		{
			num+=p.getNodenum();
			
			p=p.brother;
		}

		num++;
		
		return num;
	}
	
}
