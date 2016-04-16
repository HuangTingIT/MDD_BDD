package ´ó´´;
public class Relation 
{
	
	    String name="";
        int rnum=0;
        int vnum=0;
        int rs[][]=null;
        int change=0;
        int me=0;
        String type="";
        int[] d=null;
        
        
        boolean chtype=false;

        
        public Relation(int inrnum,int invnum,String[] t,int inme,Domain ds[])
        {
      	  vnum=invnum;
      	  rnum=inrnum;
      	  
      	  rs=new int[vnum][rnum];
      	  me=inme;

      	  
   		  d=new int[vnum];
   		  int n=4;
   		  for(int i=0;i<vnum;i++)
   		  {
   			  d[i]=Integer.parseInt(t[n]);
   			  n++;
   		  }
      	  
   		  n=5+vnum;
   		  for(int i=0;i<rnum;i++)
   		  {
   			  
   	    	  for(int j=0;j<vnum;j++)
   	    	  {
   	    		  rs[j][i]=Integer.parseInt(t[n]);
   	    		  n++;
   	    	  } 	    	  
   		  }
   		  
   	   // changeR(ds);
        }
        
        public void changeR(Domain ds[])
        {
       	
        	int rinit[][]=new int[vnum][rnum];
        	
        	int rn=0;
        	
        	System.out.println(rnum);
        	
        	for(int i=0;i<rnum;i++)
        	{        		
        		for(int j=0;j<vnum;j++)
        		{
        			int a=ds[d[j]].getV(rs[j][i]);       			
        			if(a==-1)
        			{
        				rn--;       			
        				break;
        			}
        			rinit[j][rn]=a;
        		}
        		rn++;
        	}
        	  
        	rs=new int[vnum][rn];
        	
        	rnum=rn;
        	
        	for(int i=0;i<rn;i++)
        	{
        		for(int j=0;j<vnum;j++)
        		{
        			rs[j][i]=rinit[j][i];
        		}
        	}
        }
        
}
