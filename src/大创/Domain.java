package ´ó´´;
public class Domain 
{
	String name="";
	int num=0;
	int vals[]=null;
	int me=0;
	
	public Domain(String[] ss)
	{
		  num=Integer.parseInt(ss[1]);
		  vals=new int[num];
		  me=Integer.parseInt(ss[0]);

          for(int i=0;i<num;i++)
          {
              vals[i]=Integer.parseInt(ss[i]);
          }
	}
	
	
	public int  getV(int n)
	{
		for(int i=0;i<num;i++)
		{
			if(vals[i]==n)
			{
				return i;
			}
		}		
		return -1;
	}

}
