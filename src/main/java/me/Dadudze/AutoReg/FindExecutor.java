package me.Dadudze.AutoReg;

public class FindExecutor {

    /**
     * @return Returns class, which executed method, that executed this method
     */
    public static Class getMyExecutor() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int index = 0;
        int findex = 0;
        for (StackTraceElement ste : stackTrace) {
            if(ste.getClassName().equals("me.Dadudze.AutoReg.FindExecutor")) findex = index;
            index++;
        }
        String className = stackTrace[findex+2].getClassName();
        System.out.println(className);
        try {
            return Class.forName(className);
        } catch (Exception e) {}
        return AutoRegistration.class;
    }

}
