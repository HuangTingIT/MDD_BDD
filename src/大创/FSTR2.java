package ´ó´´;
public class FSTR2 
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
	  int    lastsizelevel[][][]=null;
	  
	  int    Q[]=null;
	  int    Qnum=0;
	  int    inQ[]=null;
	  int    low=0;
	  int    high=0;
	  
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
	  
	  public FSTR2(DATA indata,Model inm)
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
		     ccurlevel=new int[m.vnum][data.constraintN];
		     
		     for(int i=0;i<data.constraintN;i++)
		     {		    	 
		    	 for(int j=0;j<m.vnum;j++)
		    	 {
                     ccurlevel[j][i]=-1;
		    	 } 
		     }
		     
		     
		     vdense=new int[data.varN][];
		     vsparse=new int[data.varN][];
		     vcur=new int[data.varN];
		     vcurlevel=new int[m.vnum][data.varN];
		     
		     for(int i=0;i<data.varN;i++)
		     {
		    	 vdense[i]=new int[data.domn[i]];
		    	 vsparse[i]=new int[data.domn[i]];
		    	 vcur[i]=data.domn[i]-1;
		    	 
		    	 for(int j=0;j<m.vnum;j++)
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
		     lastsizelevel=new int[m.vnum][data.constraintN][];
		     
		     for(int i=0;i<data.constraintN;i++)
		     {
		    	 lastsize[i]=new int[data.scopeN[i]];
		    	 
		    	 
		    	 for(int j=0;j<data.scopeN[i];j++)
		    	 {
		    		 lastsize[i][j]=data.domn[data.scopes[i][j]];
		    	 }
		    	 
		    	 
		    	 for(int j=0;j<m.vnum;j++)
		    	 {
		    		 lastsizelevel[j][i]=new int[data.scopeN[i]];
		    		 lastsizelevel[j][i][0]=-1;
		    	 }
		     }
		     
		     
		     Q=new int[data.constraintN];
		     Qnum=0;
		     inQ=new int[data.constraintN];
		     
		     for(int i=0;i<data.constraintN;i++)
		     {
		    	 inQ[i]=0;
		     }
		     		     
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
		  Qnum=0;
		  low=0;
		  high=0;
		  for(int i=0;i<data.constraintN;i++)
		  {
			  inQ[i]=1;			  
			  Q[high]=i;			  
			  high=(high+1)%data.constraintN;
			  Qnum++;
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
			  
			  /*
			  if(vdense[levelvdense[0]][0]==13&&vdense[levelvdense[1]][0]==11&&level<=3)
			  {
				  
				  for(int i=0;i<level;i++)
				  {
					  System.out.print(vdense[levelvdense[i]][0]+" ");
				  }
				 
				  uuu++;
				  System.out.println("                    aaaa"+level);
        	  
				  
				  for(int i=0;i<data.constraintN;i++)
				  {
					  System.out.print(" "+i+"_"+ccur[i]);
				  }
				  System.out.println("");
			  
				  for(int i=0;i<data.varN;i++)
				  {
					  System.out.print(" "+vcur[i]);
				  }
				  System.out.println("");	 
			  }
			  */
			   
			  //time=time1;
			  //time1=System.currentTimeMillis();
			 // if(level>4)
			 // System.out.println((time1-time)+" "+level);
			  
			  time=System.currentTimeMillis();
			  if((time-time1)>600000)
			  {
				  return false;
			  }
			  
			  
			  if(!AC(level))
			  {
				  if(level==0)
				  {
		          	  node=n;
					  return false;
				  }
				  
				  if(level==m.vnum)
				  {
					  level--;
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
		          

		          
		          while(vcur[levelvdense[level]]==0)
		          {
					  //System.out.println(level);

					  if(level==0)
					  {						 
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
			          
			          
			          for(int i=0;i<m.vs[v].cnum;i++)
			          {
			        	  int c=m.vs[v].cs[i].me;
			        	  assignedcount[c]--;
			          }
			          
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

                  Qnum=0;
                  low=0;
                  high=0;
		          for(int i=0;i<vtocN[v];i++)
		          {
		        	   Q[high]=vtoc[v][i];
		        	   inQ[Q[high]]=1;
		        	   high=(high+1)%data.constraintN;
		        	   Qnum++;
		          }
			  }
			  else
			  {
				  n++;
				  ///////////////////////////////////////////////////////////
				  //find  dom/ddeg
				  double mindom=maxd+1;
				  int minvi=level; 
				  int minv=levelvdense[level];
				  
				  for(int i=level;i<m.vnum;i++)
				  {
					   int v=levelvdense[i];
					   int ddeg=0;
					   
					   for(int j=0;j<vtocN[v];j++)
					   {
						   int cc=vtoc[v][j];//m.vs[v].cs[j].me;
						   
						   boolean ff=false;
						   
						   /*
						   for(int u=0;u<data.scopeN[cc];u++)
						   {
							   if(vcur[data.scopes[cc][u]]!=0)
							   {
								   if(ff)
								   {
									   ddeg+=1;
									   break;
								   }
								   ff=true;
							   }
						   }
						   */
						   
						   
						   if(assignedcount[cc]+1<m.cs[cc].vnum)
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
		        
		          
		          for(int i=0;i<m.vs[v].cnum;i++)
		          {
		        	  int c=m.vs[v].cs[i].me;
		        	  assignedcount[c]++;
		          }
		          
		          
		          level++;
		          if(level==m.vnum)
		          {
	        		   int t[]=new int[data.varN];
	        		   
		        	   for(int i=0;i<data.varN;i++)
		        	   {
		        		   t[i]=vdense[i][0];
		        		   //System.out.print(t[i]+" ");
		        	   }
		        	   
		        	   for(int j=0;j<=vcur[v];j++)
		        	   {
		        		   t[v]=vdense[v][j];
		        		   m.sol.add(t);
		        		   m.soln++;
		        	   }
		        	   
		        	  // System.out.println(m.soln);
		        	   continue;
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
					  
	                  Qnum=0;
	                  low=0;
	                  high=0;
			          for(int i=0;i<vtocN[v];i++)
			          {
			        	   Q[high]=vtoc[v][i];
			        	   inQ[vtoc[v][i]]=1;
			        	   Qnum++;
			        	   high=(high+1)%data.constraintN;
			          }
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
		  
		  if(level==m.vnum)
		  {
			  return false;
		  }
		  while(Qnum!=0)
		  {
			  Qnum--;
			  int c=Q[low];
			  int j=low;
			 
			  
			  long time=System.currentTimeMillis();
			  int key=ccur[Q[low]];
			  int now=low;
			  
			  for(int i=0;i<Qnum;i++)
			  {
				  if(key>ccur[Q[j]])
				  {
					  key=ccur[Q[j]];
					  now=j;
				  }
				  j=(j+1)%data.constraintN;
			  }
			  
			  c=Q[now];
			  Q[now]=Q[low];
			  
			 // rivestime+=(System.currentTimeMillis()-time);
			   
			  
			  low=(low+1)%data.constraintN;
			  //Q[0]=Q[Qnum];
			  //System.out.println(c);
			  int[] re=revise(c,level);
			  
			  if(ccur[c]<0)
			  {
				  
				  inQ[c]=0;
				  j=low;
				  for(int i=0;i<Qnum;i++)
				  {
					  inQ[Q[j]]=0;
					  j=(j+1)%data.constraintN;
				  }
				  Qnum=0;
				  //System.out.println(" "+c);
				 // long time2=System.currentTimeMillis();
				  //System.out.println((time2-time)+"ssss");
				  return false;
			  }
			  
			  if(re!=null)
			  {
				  for(int i=0;i<re.length;i++)
				  {
					  
					  for(j=0;j<vtocN[re[i]];j++)
					  {   
						     //System.out.println(m.vs[re[i]].me+" "+Qnum);

						     if(inQ[vtoc[re[i]][j]]==0)
						     {
						    	    Q[high]=vtoc[re[i]][j];     
						    	    Qnum++;
					                high=(high+1)%data.constraintN;
						    	    inQ[vtoc[re[i]][j]]=1;
						     }  
					  }

				  }
			  }
			  
			  inQ[c]=0;
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
		  
		  for(int i=0;i<=ccur[c];i++)
		  {
			    t=cdense[c][i];
			    f=true;
                for(int j=0;j<Svalnum;j++)
                {
                	 int v=Sval[j];
                	 
                	//System.out.println(v+" "+m.vs[vmark[v]].d.me+" "+tt[v][t]+" "+c);
                     
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
                	 int a=cdense[c][ccur[c]];
                	 cdense[c][ccur[c]]=cdense[c][i];
                	 cdense[c][i]=a;
                	 
                	 if(ccurlevel[level][c]==-1)
                	 {
                		 ccurlevel[level][c]=ccur[c];
                	 }
                	 
                	 ccur[c]--;
                	 i--;	 
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
}
