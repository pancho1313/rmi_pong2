package dev;

public class MyUtil {
	
	public String getArg(String[] args, int argPos, String defaultArg, String notFoundMessage){
		String arg;
		if(args.length >= argPos+1){
			arg = args[argPos];
		}else{
			System.out.println( notFoundMessage + " (default = "+defaultArg+")");
			arg = defaultArg;
		}
		return arg;
	}
	
	//mostrar mensaje en consola?
	public void localMessage(String m){
		System.out.println(m);
	}
}
