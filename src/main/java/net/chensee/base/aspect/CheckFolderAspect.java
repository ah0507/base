package net.chensee.base.aspect;

import net.chensee.base.action.folder.mapper.FolderDao;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.annotation.CheckFolderAnnontation;
import net.chensee.base.common.po.BaseInfoPo;
import net.chensee.base.utils.ResFolderUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @author yanfa-1
 * @data 2019年10月15日 10:27:33
 * 切面类：检查文件夹ID是否为空且是否为叶子节点
 */
@Aspect
@Component
public class CheckFolderAspect {
    private static final Logger logger = LoggerFactory.getLogger(CheckFolderAspect.class);

    @Resource
    private FolderDao folderDao;


    //Controller层切点
    //@Pointcut("execution(* net.chensee.base.action.*.controller.*(..))")
    @Pointcut("@annotation(net.chensee.base.annotation.CheckFolderAnnontation)")
    public void cutMethod() {

    }

    @Before("cutMethod()")
    public void before(JoinPoint joinPoint) throws NoSuchMethodException {
        logger.debug("-------切点-------"+joinPoint);
        Object[] args = joinPoint.getArgs();

        Class[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method = joinPoint.getSignature().getDeclaringType().getMethod(joinPoint.getSignature().getName(),parameterTypes);
        CheckFolderAnnontation checkFolderAnnontation = method.getAnnotation(CheckFolderAnnontation.class);
        int[] ints = checkFolderAnnontation.value();
        //获取所有非leaf文件夹及其下属的leaf文件夹
        Map<Long, Set<Long>> allFolderAndLeafs = ResFolderUtils.getAllFolderAndLeafs();
        Map<Long, FolderVo> allFolders = ResFolderUtils.getAllFolders();
        boolean isLeaf = false;
        for (int index : ints) {
            if (index >= args.length) {
                continue;
            }
            Object o = args[index];
            if (BaseInfoPo.class.isInstance(o)) {
                BaseInfoPo po = (BaseInfoPo)o;
                Long folderId = po.getFolderId();
                //判断folderId是否符合标准
                if (folderId == null) {
                    throw new RuntimeException("传入的参数无folderId");
                }
                if (!allFolders.containsKey(folderId)) {
                    throw new RuntimeException("传入的参数folderId不正确");
                }
                if (allFolderAndLeafs.containsKey(folderId)) {
                    throw new RuntimeException("传入的folderId不是叶子节点1");
                }
                for (Map.Entry<Long, Set<Long>> entry : allFolderAndLeafs.entrySet()) {
                    Set<Long> values = entry.getValue();
                    if (values.contains(folderId)) {
                        isLeaf = true;
                        break;
                    }
                }
                if (!isLeaf) {
                    throw new RuntimeException("传入的folderId不是叶子节点2");
                }
            }
        }
    }


}
