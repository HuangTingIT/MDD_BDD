package ´ó´´;
public class FSTR2_domv 
{
	  Model  m=null;
	  
	  DATA  data=null;
	  
	  int    cdense[][]=null;
	  int    ccur[]=null;
	  int    ccurlevel[][]=null;
	  
	  int    vsparse[][]=null;
	  int    vdense[][]=null;
	  int    vcur[]=null;
	  int    vcurlevel[][]=null;
	  
	  int    lastsize[][]=null;
	  
	  /////////////////////////////////////////////
	  int    Q[]=null;
	  int    Qnum=0;
	  int    inQ[]=null;
	  int    low=0;
	  int    high=0;
	  //
	  long 	 vT[]=null;
	  long    vvT[]=null;
	  long	 cT[]=null;
	  long    backT=0;
	  int    heap[]=null;
	  int    heapn=0;
	  int    inheap[]=null;
	  /////////////////////////////////////////////
	  int    Ssup[]=null; 
	  int    Sval[]=null;
	  
	  int    Ssupnum=0;
	  int    Svalnum=0;
	  
	  
	  int    levelvdense[]=null;
	  int    levelvsparse[]=null;
	  
	  
	  int    assignedcount[]=null;
	  
	  long rivestime=0;
	  
	  ///////////////////////////////////////////////////////////////////////////////////////
	  int vinc[][]=null;	  
	  int vtoc[][]=null;
	  int vtocN[]=null;
	  int vmark[][]=null;
	  	  
	  int maxd=0;
	  
	  int node =0;
	  
	  int uuu=0;
	  
	  ///////////////////////////////////////////////////////////////////////////////////////
	  long mask0[]=null;
	  long mask1[]=null;
	  
	  
	  
	  ///////////////////////////////////////////////////////////////////////////////////////
	  
	  public FSTR2_domv(DATA indata,Model inm)
	  {
		     m=inm;
           data=indata;
           
           vtoc=new int[data.varN][];
           vtocN=new int[data.varN];
           
           for(int i=0;i<data.varN;i++)
           {
          	    vtocN[i]=0;
           }
           
           for(int i=0;i<data.constraintN;i++)
           {
          	 	for(int j=0;j<data.scopeN[i];j++)
          	 	{
          	 		int v=data.scopes[i][j];
          	 		vtocN[v]++;
          	 	}
           }
           
           for(int i=0;i<data.varN;i++)
           {
          	 	vtoc[i]=new int[vtocN[i]];
          	    vtocN[i]=0;
          	    
           }
           
           for(int i=0;i<data.constraintN;i++)
           {
          	 	for(int j=0;j<data.scopeN[i];j++)
          	 	{
          	 		int v=data.scopes[i][j];
          	 		vtoc[v][vtocN[v]]=i;
          	 		vtocN[v]++;
          	 	}
           }
           
           for(int i=0;i<data.varN;i++)
           {
          	 	if(data.domn[i]>maxd)
          	 	{
          	 		maxd=data.domn[i];
          	 	}
           }
           
           /////////////////////////////////////
		     cdense=data.cdense;
		     ccur=data.ccur;
		     ccurlevel=new int[data.varN+1][data.constraintN];
		     
		     for(int i=0;i<data.constraintN;i++)
		     {		    	 
		    	 for(int j=0;j<data.varN+1;j++)
		    	 {
                   ccurlevel[j][i]=-1;
		    	 } 
		     }
		     
		     
		     vdense=new int[data.varN][];
		     vsparse=new int[data.varN][];
		     vcur=new int[data.varN];
		     vcurlevel=new int[data.varN+1][data.varN];
		     
		     for(int i=0;i<data.varN;i++)
		     {
		    	 vdense[i]=new int[data.domn[i]];
		    	 vsparse[i]=new int[data.domn[i]];
		    	 vcur[i]=data.domn[i]-1;
		    	 
		    	 for(int j=0;j<data.varN+1;j++)
		    	 {
		    		 vcurlevel[j][i]=-1;
		    	 }
		    	 
		    	 for(int j=0;j<data.domn[i];j++)
		    	 {
		    		 vdense[i][j]=data.domn[i]-1-j;
		    		 vsparse[i][data.domn[i]-1-j]=j;
		    	 }
		     }
		     
		     
		     lastsize=new int[data.constraintN][];
		     
		     for(int i=0;i<data.constraintN;i++)
		     {
		    	 lastsize[i]=new int[data.scopeN[i]];
		    	 
		    	 
		    	 for(int j=0;j<data.scopeN[i];j++)
		    	 {
		    		 lastsize[i][j]=data.domn[data.scopes[i][j]];
		    	 }
		     }
		     //////////////////////////////////////////////////
		     
		     Q=new int[data.constraintN];
		     Qnum=0;
		     inQ=new int[data.constraintN];
		     
		     for(int i=0;i<data.constraintN;i++)
		     {
		    	 inQ[i]=0;
		     }
		     //
		     heap=new int[data.varN];
		     inheap=new int[data.varN];
		     vT=new long[data.varN];
		     cT=new long[data.constraintN];
		     vvT=new long[data.varN];
		     ///////////////////////////////////////////////////
			 Ssup=new int[data.varN]; 
			 Sval=new int[data.varN];
			 
			 
			 levelvdense=new int[data.varN];
			 levelvsparse=new int[data.varN];
			 
			 for(int i=0;i<data.varN;i++)
			 {
				 levelvdense[i]=i;
				 levelvsparse[i]=i;
			 }	
			 
			 assignedcount=new int[m.cnum];
	  }
	  
	  	  
	  boolean  MAC()
	  {
		  heapn=0;
		  backT=1;
		  for(int i=0;i<data.varN;i++)
		  {
			  addheap(i);
		  }  
		  
        for(int i=0;i<data.constraintN;i++)
        {
      	  for(int j=0;j<m.vnum;j++)
      	  {
       		    ccurlevel[j][i]=-1;
      	  }
      	  
 		      for(int r=0;r<data.scopeN[i];r++)
		     {
		    		lastsize[i][r]=-1;	   		 
		     }
        }
		   
		   
        for(int i=0;i<data.varN;i++)
        {
      	  for(int j=0;j<m.vnum;j++)
      	  {
       		    vcurlevel[j][i]=-1;
      	  }
        }
		  
		  int level=0;

		  int n=0;
		  long time=0;
		  long time1=System.currentTimeMillis();
		  
		  
		  while(level>=0)
		  {		
			  
			  time=System.currentTimeMillis();
			  if((time-time1)>600000)
			  {
				  return false;
			  }
			  
	   
			  if(!AC(level))
			  {
				  if(level==0)
				  {
		        	  System.out.println("1no");
		          	  System.out.println("node: "+n);
		          	  node=n;
					  return false;
				  }
				  
		          for(int i=0;i<data.varN;i++)
		          {
		         		    if(vcurlevel[level][i]!=-1)
		         		    {
		         		    	vcur[i]=vcurlevel[level][i];
		         		    	vcurlevel[level][i]=-1;
		         		    }
		          }
				  
		          for(int i=0;i<data.constraintN;i++)
		          {
		         		    if(ccurlevel[level][i]!=-1)
		         		    {
		         		    	ccur[i]=ccurlevel[level][i];
		         		    	ccurlevel[level][i]=-1;
		         		    }
		         		    
	         		    	for(int j=0;j<data.scopeN[i];j++)
	         		    	{
	         		    		lastsize[i][j]=vcur[data.scopes[i][j]]+1;
	         		    	}
		          }
		          
		          
		          level--;
		          int v=levelvdense[level];
		          
		          /*
		          for(int i=0;i<m.vs[v].cnum;i++)
		          {
		        	  int c=m.vs[v].cs[i].me;
		        	  assignedcount[c]--;
		          }
		          */
		          while(vcur[levelvdense[level]]==0)
		          {
					  //System.out.println(level);

					  if(level==0)
					  {
						  
			        	   System.out.println("2no");
			           	   System.out.println("node: "+n);
						   node=n;
						  return false;
					  }
					  
			          for(int i=0;i<data.varN;i++)
			          {
			         		    if(vcurlevel[level][i]!=-1)
			         		    {
			         		    	vcur[i]=vcurlevel[level][i];
			         		    	vcurlevel[level][i]=-1;
			         		    }
			          }
			          
			          for(int i=0;i<data.constraintN;i++)
			          {
			         		    if(ccurlevel[level][i]!=-1)
			         		    {
			         		    	ccur[i]=ccurlevel[level][i];
			         		    	ccurlevel[level][i]=-1;
			         		    }	
			         		    
			         		    
		         		    	for(int j=0;j<data.scopeN[i];j++)
		         		    	{
		         		    		lastsize[i][j]=vcur[data.scopes[i][j]]+1;
		         		    	}
			          }
			          
			          level--;
			          
			          v=levelvdense[level];
			          
			          /*
			          for(int i=0;i<m.vs[v].cnum;i++)
			          {
			        	  int c=m.vs[v].cs[i].me;
			        	  assignedcount[c]--;
			          }
			          */
		          }
		          
		          v=levelvdense[level];
		          
		          int a=vdense[v][vcur[v]];
                vsparse[v][a]=0;
                vsparse[v][vdense[v][0]]=vcur[v];
                vdense[v][vcur[v]]=vdense[v][0];
                vdense[v][0]=a;
		          
                if(vcurlevel[level][v]==-1)
                {
              	  vcurlevel[level][v]=vcur[v];
                }
                
                vcur[v]--;

		          for(int i=0;i<data.hvtocN[v];i++)
		          {
		        	  int c=data.hvtoc[v][i];
		        	  assignedcount[c]--;
		          }
                
		          addheap(v);
			  }
			  else
			  {
				  n++;
				  ///////////////////////////////////////////////////////////
				  //find  dom/ddeg
				  double mindom=maxd+1;
				  int minvi=level;
				  int minv=levelvdense[level];
				  
				  for(int i=level;i<data.varN;i++)//data.varN
				  {
					   int v=levelvdense[i];
					   int ddeg=0;
					   
					   for(int j=0;j<data.hvtocN[v];j++) //vtocN[v]
					   {
						   int cc=data.hvtoc[v][j];//m.vs[v].cs[j].me;//vtoc[v][j];
						   					   
						   if(assignedcount[cc]+1<data.cscopeN[cc])
						   {
							   ddeg+=1;							   
						   }
					   }
					   
					   if(vcur[v]>0&&ddeg>0&&(1.0*(vcur[v]+1)/ddeg<mindom||(1.0*(vcur[v]+1)/ddeg==mindom&&v<minv)))
					   {
						   mindom=1.0*(vcur[v]+1)/ddeg;
						   minvi=i;
						   minv=v;
					   }
					   else if(mindom==maxd+1&&v<minv)
					   {
						   minvi=i;
						   minv=v;
					   }
					   
				  }
				  
				  int a=levelvdense[level];
				  levelvdense[level]=levelvdense[minvi];
				  
				  levelvsparse[a]=minvi;
				  levelvsparse[levelvdense[minvi]]=level;
				  
				  levelvdense[minvi]=a;
				  
				  ///////////////////////////////////////////////////////////
		          int v=levelvdense[level];
		        
		          
		          for(int i=0;i<data.hvtocN[v];i++)
		          {
		        	  int c=data.hvtoc[v][i];
		        	  assignedcount[c]++;
		          }
		          
		          
		          level++;
		          if(level==data.varN)
		          {
		        	   System.out.println("yes");
		        	   for(int i=0;i<data.varN;i++)
		        	   {
		        		   int vv=levelvdense[i];
		        		   System.out.print("<"+"x"+vv+" "+vdense[vv][0]+">   ");
		        
		        	   }
		        	   System.out.println("");
		           	   System.out.println("node: "+n+" "+rivestime);
		        	   node=n;
		        	   return true;
		          }
		          

                if(vcur[v]!=0)
		          {
     		    	  vcurlevel[level][v]=vcur[v];
     		    	  int val=maxd+1;
     		    	  int valn=0;
     		    	  for(int i=0;i<=vcur[v];i++)
     		    	  {
     		    		  if(vdense[v][i]<val)
     		    		  {
     		    			  valn=i;
     		    			  val=vdense[v][i];
     		    		  }
     		    	  }
     		    	  
     		    	  
     		    	  
     		    	a=vdense[v][valn];
                    vsparse[v][a]=0;
                    vsparse[v][vdense[v][0]]=valn;
                    vdense[v][valn]=vdense[v][0];
                    vdense[v][0]=a;
     		    	  
					  vcur[v]=0;
					  


					  addheap(v);
		          }
			  }
		  }
 	      System.out.println("node: "+n);
		  
	
		  return false;
	  }
	  
	  boolean AC(int level)
	  {
//long time=System.currentTimeMillis();
		  int k=0;
		  while(heapn!=0)
		  {

			  int x=gethead();
			  
			  for(int i=0;i<vtocN[x];i++)
			  {
				  int c=vtoc[x][i];
				  
				  if(vvT[x]<cT[c])
				  {
					  continue;
				  }
				  int[] re=revise(c,level);
				  
				  if(ccur[c]<0)
				  {
					  
					  for(int j=0;j<heapn;j++)
					  {
						  int v=heap[j];
						  inheap[v]=-1;
					  }
					  heapn=0;
					  
					  return false;
				  }
				 				  
				  if(re!=null)
				  {
					  for(int j=0;j<re.length;j++)
					  {
						  int v=re[j];						 
						  
						  if(inheap[v]==-1)
						  {
							  addheap(v);
						  }
						  else
						  {
							  heapup(inheap[v]);
						  }
					  }
				  }
				  
				  cT[c]=backT;
				  backT++;
			  }			 			  
		  }
		 // long time2=System.currentTimeMillis();
		  //System.out.println((time2-time)+"ssss");
		  return true;
	  }
	  
	  int[] revise(int c,int level)
	  {

	      int vmark[]=data.scopes[c];		  
	      int gacnum[]=new int[data.scopeN[c]];        

        
		  Ssupnum=0;
		  Svalnum=0;
		
		  int densec[]=cdense[c];
		 	  
		  //System.out.println(c+" "+ccur[c]);
		  for(int i=0;i<data.scopeN[c];i++)
		  {
			  gacnum[i]=-1;			  
			  if(lastsize[c][i]!=vcur[vmark[i]]+1)
			  {
				  Sval[Svalnum]=i;
				  Svalnum++;
				  lastsize[c][i]=vcur[vmark[i]]+1;
			  }
			  
            if(levelvsparse[vmark[i]]>=level)
            {
          	  Ssup[Ssupnum]=i;
          	  Ssupnum++;
            }
		  }
		  
		  if(Svalnum==0)return null;
		  
		  boolean f=true;
		  int  t=0;
		  int  tt[][]=data.relations[c];
		  
		  for(int i=ccur[c];i>=0;i--)
		  {
			    t=densec[i];
			    f=true;
              for(int j=0;j<Svalnum;j++)
              {
              	 int v=Sval[j];
              	   //System.out.println(tt[v][t]+" "+data.scopes[c][v]+" "+data.domn[vmark[v]]+" "+t+" "+c+" "+j);
                   if(vsparse[vmark[v]][tt[v][t]]>vcur[vmark[v]])
                   {
                  	 f=false;
                  	 break;
                   }	
              }
              
              /*
          	System.out.println("---------------------"+m.cs[c].r.rnum);
          	for(int j=0;j<t.length;j++)
          	{
          		System.out.print(t[j]+" ");
          	}
          	System.out.println("");
              */
              
              if(f)
              {
              	for(int j=0;j<Ssupnum;j++)
              	{
              		int v=Ssup[j];
              		int vk=vmark[v];
              		int va=tt[v][t];
      				int k=vsparse[vk][va];
              		if(k>gacnum[v])
              		{                			
              			gacnum[v]++; 
              			if(gacnum[v]==vcur[vk])
              			{
              					Ssupnum--;
              					Ssup[j]=Ssup[Ssupnum];
              					j--;                			
              			}
              			else
              			{
              				vdense[vk][k]=vdense[vk][gacnum[v]];
              				vsparse[vk][vdense[vk][gacnum[v]]]=k;
              				vdense[vk][gacnum[v]]=va;
              				vsparse[vk][va]=gacnum[v];     
              			}
              		}
              	}
              }
              else
              {
              	 int a=densec[ccur[c]];
              	 densec[ccur[c]]=densec[i];
              	 densec[i]=a;
              	 
              	 if(ccurlevel[level][c]==-1)
              	 {
              		 ccurlevel[level][c]=ccur[c];
              	 }
              	 
              	 ccur[c]--; 
              }
              
		  }
		  
/*
		  for(int r=0;r<data.scopeN[c];r++)
		  {
			  System.out.print(" "+gacnum[r]+"_"+vcur[vmark[r]]);
		  }
		  System.out.println("");
	*/	  
		  int[] re=null;
		  if(Ssupnum>0)
		  {
			  re=new int[Ssupnum]; 
		  }
		  		  
        for(int r=0;r<Ssupnum;r++)
        {
      	  int i=Ssup[r];
      	  
      	  re[r]=vmark[i];
      	  
      	  if(vcurlevel[level][vmark[i]]==-1)
      	  {
      		  vcurlevel[level][vmark[i]]=vcur[vmark[i]];
      	  }  	  
      	  vcur[vmark[i]]=gacnum[i];       	  
      	  lastsize[c][i]=vcur[vmark[i]]+1;       	         	  
  		 // System.out.print(gacnum[i]+" ");
        }
        
		  //System.out.println("-----"+ccur[c]);
		  ///System.out.println(c+" "+ccur[c]);
             
		  return re;
	  }  


		
		void addheap(int v)
		{
			int p=heapn;
			int q=p;
			vT[v]=backT;
			vvT[v]=backT;
			backT++;
			
			heapn++;
			
			while(p!=0)
			{
				p=(p-1)/2;
				int vv=heap[p];
				
				if(vcur[vv]<vcur[v]|(vcur[vv]==vcur[v]&&vT[vv]<vT[v]))
				{
					break;
				}
				
				heap[q]=vv;
				inheap[vv]=q;

				q=p;
			}
			inheap[v]=q;
			heap[q]=v;
		}
		
		int gethead()
		{
			int a=heap[0];
			heapn--;
			
			heap[0]=heap[heapn];
			inheap[heap[heapn]]=0;
			heapdown(0);
			
			inheap[a]=-1;
			return a;
		}
		
		void heapup(int p)
		{
			int q=p;
			int v=heap[p];
			//System.out.println(y+" "+v);
			vvT[v]=backT;
			backT++;
			
			while(p>0)
			{
				p=(p-1)/2;
				int vv=heap[p];
				
				if(vcur[vv]<vcur[v]|(vcur[vv]==vcur[v]&&vT[vv]<vT[v]))
				{
					break;
				}
				
				heap[q]=vv;
				inheap[vv]=q;
				q=p;
			}
			

			
			heap[q]=v;
			inheap[v]=q;
			
		}
		
		void heapdown(int p)
		{
			int v= heap[p];
		    int q=p;

			while(true)
			{
				q=p*2+1;
				
				if(q>heapn-1)
				{
					heap[p]=v;
					inheap[v]=p;
					break;
				}
				
				if(q==heapn-1)
				{
					
					int vv=heap[q];
					
					
					if(vcur[vv]>vcur[v]|(vcur[v]==vcur[vv]&&vT[v]<vT[vv]))
					{
						heap[p]=v;
						inheap[v]=p;
						break;
					}
					
					heap[p]=vv;
					inheap[vv]=p;
					
					inheap[v]=q;
					heap[q]=v;
					break;
				}
				
				int vvv=heap[q+1];
				int vv=heap[q];
				
				if(vcur[vvv]<vcur[vv]|(vcur[vv]==vcur[vvv]&&vT[vv]>vT[vvv]))
				{
					q=q+1;
					vv=vvv;
				}
				
				if(vcur[vv]>vcur[v]|(vcur[v]==vcur[vv]&&vT[v]<vT[vv]))
				{
					heap[p]=v;
					inheap[v]=p;
					break;
				}
				
				heap[p]=vv;
				inheap[vv]=p;
				
				p=q;
			}
		}
}
