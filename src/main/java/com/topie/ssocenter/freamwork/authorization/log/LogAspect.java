package com.topie.ssocenter.freamwork.authorization.log;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.freamwork.authorization.model.Log;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.LogService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

@Component
@Aspect 
public class LogAspect {  
	Logger logger = Logger.getLogger(LogAspect.class);
      
    @Autowired  
    private LogService logService;//日志记录Service  
      
    /** 
     * 添加业务逻辑方法切入点 
     */  
    @Pointcut("(execution(* com.**.service.*.insert*(..))||"
    		+ "execution(* com.**.service.*.save*(..)))&&"
    		+ "(!execution(* com.**.service.LogService*.save*(..)))")  
    public void insertServiceCall() { }  
      
    /** 
     * 修改业务逻辑方法切入点 
     */  
    @Pointcut("(execution(* com.**.service.*.update*(..)))||"
    		+ "(!execution(* com.**.service.LogService*.update*(..)))")  
    public void updateServiceCall() { }  
      
    /** 
     * 删除影片业务逻辑方法切入点 
     */  
//    @Pointcut("execution(* com.xxx.service.FilmService.deleteFilm(..))")  
//    public void deleteFilmCall() { }  
    
    @Pointcut("execution(* com.**.controller.*.*(..))")
	public void allCall() {
	}
      
    /** 
     * 管理员添加操作日志(后置通知) 
     * @param joinPoint 
     * @param rtv 
     * @throws Throwable 
     */  
    @AfterReturning(value="insertServiceCall()", argNames="rtv", returning="rtv")  
    public void insertServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable{  
          
          
        //判断参数  
        if(joinPoint.getArgs() == null){//没有参数  
            return;  
        }  
         
        //获取方法名  
        String methodName = joinPoint.getSignature().getName();  
          
        //获取操作内容  
        String opContent = adminOptionContent(joinPoint.getArgs(), methodName);  
          
        //创建日志对象  
        insertLog("添加",opContent,"");
    }  
      
    
     private void insertLog(String title,String content,String nll) {
    	 Log log = new Log();  
    	 //获取登录管理员 
    	 OrangeSideSecurityUser user = SecurityUtils.getCurrentSecurityUser(); 
         log.setUser(user.getUsername()+"["+user.getId()+"]");//设置管理员id  
         log.setDate(DmDateUtil.Current());//操作时间  
         log.setContent(content);//操作内容  
         log.setTitle(title);//操作  
         log.setType(LogService.CAOZUO);
         String ip = SecurityUtils.getCurrentIP();
         log.setIp(ip);
         logService.save(log);//添加日志  
		
	}

	/** 
     * 管理员修改操作日志(后置通知) 
     * @param joinPoint 
     * @param rtv 
     * @throws Throwable 
     */  
    @AfterReturning(value="updateServiceCall()", argNames="rtv", returning="rtv")  
    public void updateServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable{  
          
        //判断参数  
        if(joinPoint.getArgs() == null){//没有参数  
            return;  
        }  
          
        //获取方法名  
        String methodName = joinPoint.getSignature().getName();  
          
        //获取操作内容  
        String opContent = adminOptionContent(joinPoint.getArgs(), methodName);  
          
        //创建日志对象  
        insertLog("修改",opContent,"");
    }  
      
    
    @AfterThrowing(value = "allCall()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, RuntimeException e) {
    	  //获取方法名  
        String methodName = joinPoint.getSignature().getName();  
          
        //获取操作内容  
        String opContent =methodName+"[throw 产生异常的方法名称]：  " + e.getMessage() + ">>>>>>>" + e.getCause();
        //创建日志对象  
        insertLog("异常",opContent,"");
	}

	@Around(value = "allCall()")
	public Object aroundCall(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		long procTime = System.currentTimeMillis();
		try {
			result = pjp.proceed();
			procTime = System.currentTimeMillis() - procTime;
			logger.info(pjp.getTarget().getClass().getName() + "."
					+ pjp.getSignature().getName() + "耗时：" + procTime + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    /** 
     * 管理员删除影片操作(环绕通知)，使用环绕通知的目的是 
     * 在影片被删除前可以先查询出影片信息用于日志记录 
     * @param joinPoint 
     * @param rtv 
     * @throws Throwable 
     *//*  
    @Around(value="deleteFilmCall()", argNames="rtv")  
    public Object deleteFilmCallCalls(ProceedingJoinPoint pjp) throws Throwable {  
          
        Object result = null;  
         //环绕通知处理方法  
         try {  
              
            //获取方法参数(被删除的影片id)  
            Integer id = (Integer)pjp.getArgs()[0];  
            Film obj = null;//影片对象  
            if(id != null){  
                //删除前先查询出影片对象  
                obj = filmService.getFilmById(id);  
            }  
              
            //执行删除影片操作  
            result = pjp.proceed();  
              
            if(obj != null){  
                  
                //创建日志对象  
                Log log = new Log();  
                log.setUserid(logService.loginUserId());//用户编号  
                log.setCreatedate(new Date());//操作时间  
                  
                StringBuffer msg = new StringBuffer("影片名 : ");  
                msg.append(obj.getFname());  
                log.setContent(msg.toString());//操作内容  
                  
                log.setOperation("删除");//操作  
                  
                logService.log(log);//添加日志  
            }  
              
         }  
         catch(Exception ex) {  
            ex.printStackTrace();  
         }  
           
         return result;  
    }*/  
      
    /** 
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 
     * 将参数值拼接为操作内容 
     */  
    public String adminOptionContent(Object[] args, String mName) throws Exception{  
  
        if (args == null) {  
            return null;  
        }  
          
        StringBuffer rs = new StringBuffer();  
        rs.append(mName);  
        String className = null;  
        int index = 1;  
        // 遍历参数对象  
        for (Object info : args) {  
              
            //获取对象类型  
            className = info.getClass().getName();  
            className = className.substring(className.lastIndexOf(".") + 1);  
            rs.append("[参数" + index + "，类型：" + className + "，值：");  
              
            // 获取对象的所有方法  
            Method[] methods = info.getClass().getDeclaredMethods();  
              
            // 遍历方法，判断get方法  
            for (Method method : methods) {  
                  
                String methodName = method.getName();  
                // 判断是不是get方法  
                if (methodName.indexOf("get") == -1) {// 不是get方法  
                    continue;// 不处理  
                }  
                  
                Object rsValue = null;  
                try {  
                      
                    // 调用get方法，获取返回值  
                    rsValue = method.invoke(info);  
                      
                    if (rsValue == null) {//没有返回值  
                        continue;  
                    }  
                      
                } catch (Exception e) {  
                    continue;  
                }  
                  
                //将值加入内容中  
                rs.append("(" + methodName + " : " + rsValue + ")");  
            }  
              
            rs.append("]");  
              
            index++;  
        }  
          
        return rs.toString();  
    }  
      
}  
