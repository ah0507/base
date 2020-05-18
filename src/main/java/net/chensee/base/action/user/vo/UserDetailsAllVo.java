package net.chensee.base.action.user.vo;

import lombok.Getter;
import lombok.Setter;
import net.chensee.base.action.dept.vo.DeptVo;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.component.SimpleResourceGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @program: base
 * @author : xx
 * @create: 2019-07-01 16:27
 * @description: UserDetail
 */
public class UserDetailsAllVo extends UserDetailsVo {

    /**登陆类型*/
    @Getter
    @Setter
    private Integer isUsed;
    /**状态*/
    @Getter
    @Setter
    private Integer status;
    /**
     * 用户所有的资源
     */
    @Getter
    @Setter
    private List<ResourceVo> resourceVos;

    /**
     * 用户所属的部门
     */
    @Getter
    @Setter
    private List<DeptVo> deptVos;


    public void authorities() {
        if (this.resourceVos != null && this.resourceVos.size() > 0) {
            Collection<SimpleResourceGrantedAuthority> authorities = new ArrayList<>();
            for (ResourceVo resourceVo : this.resourceVos) {
                String name = resourceVo.getName();
                String method = resourceVo.getMethod();

                if (name == null || name.isEmpty() || method == null || method.isEmpty()) {
                    continue;
                }

                authorities.add(new SimpleResourceGrantedAuthority(name, method));
            }
            this.setAuthorities(authorities);
        }
    }

    // TODO 用于扩展用户基本信息
}
