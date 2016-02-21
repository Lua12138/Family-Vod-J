package vod.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import vod.player.DreamMediaList;

/**
 * 环绕AOP，暂时不使用
 */
@Aspect
public class MediaListAspect {
    //@Pointcut("execution(* vod.player.DreamMediaList.x*(..))")
    private void anyDreamMethod() {
    }

    //@Pointcut("execution(* vod.player.DreamMediaListPlayer.setMediaList(..))")
    private void setMediaList() {
    }

    //@Around("setMediaList()")
    public Object replaceReturnValue(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();

        if (target instanceof DreamMediaList) {
            DreamMediaList list = (DreamMediaList) target;
            //System.out.println(list.mediaListInstance());
            return joinPoint.proceed(new Object[]{list});
        }
        return joinPoint.proceed();
    }

    //@Around("anyDreamMethod()")
    public Object lockAndUnlock(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("11111111");

//        Class clazz = joinPoint.getThis().getClass();
//        Method lock = clazz.getDeclaredMethod("lock");
//        Method unlock = clazz.getDeclaredMethod("unlock");
//        lock.setAccessible(true);
//        unlock.setAccessible(true);
//
//        lock.invoke(clazz);
        Object object = joinPoint.proceed();//执行该方法
//        unlock.invoke(clazz);
        System.out.println("22222222");
        return object;
    }

//    private void lock() {
//        logger.debug("lock()");
//        libvlc.libvlc_media_list_lock(mediaListInstance);
//    }
//
//    private void unlock() {
//        logger.debug("unlock()");
//        libvlc.libvlc_media_list_unlock(mediaListInstance);
//    }
}
