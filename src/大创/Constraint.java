package ´ó´´;
public class Constraint
{
	  String name="";
      int rnum=0;
      int vnum=0;
      int me=0;
      double p=1;
      Variable vs[]=null;
      int  vmark[]=null;
      int deg=0;
      int mind=0;
      
      Relation r=null;
      
      public Constraint(int invnum,Relation inr,Variable invs[],int inme,Model m)
      {
    	   vnum=invnum;
    	   vs=invs;
    	   r=inr;
    	   rnum=r.rnum;
    	   me=inme;
    	   
    	   vmark=new int[vnum];   	  
    	   
    	   mind=vs[0].d.num;
           for(int i=0;i<vnum;i++)
           {
        	   vmark[i]=vs[i].me;
        	   vs[i].addConstraint(this);
        	   p*=vs[i].d.num;
        	   if(vs[i].d.num<mind)
        	   {
        		   mind=vs[i].d.num;
        	   }
           }
    	   
      }

}
