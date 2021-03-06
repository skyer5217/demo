package com.oven.core.menu.controller;

import com.oven.common.constant.AppConst;
import com.oven.common.constant.PermissionCode;
import com.oven.common.enumerate.ResultEnum;
import com.oven.core.base.controller.BaseController;
import com.oven.core.menu.service.MenuService;
import com.oven.core.menu.vo.Menu;
import com.oven.framework.exception.MyException;
import com.oven.framework.limitation.Limit;
import com.oven.framework.limitation.LimitType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 菜单控制层
 *
 * @author Oven
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Resource
    private MenuService menuService;

    /**
     * 去到菜单管理页面
     */
    @RequestMapping("/index")
    @RequiresPermissions(PermissionCode.MENU_MANAGER)
    public String index() {
        return "menu/menu";
    }

    /**
     * 分页获取菜单树形表格内容
     */
    @ResponseBody
    @RequestMapping("/getMenuTreeTableData")
    @RequiresPermissions(PermissionCode.MENU_MANAGER)
    public Object getMenuTreeTableData() throws MyException {
        try {
            return super.success(menuService.getMenuTreeTableData());
        } catch (Exception e) {
            throw new MyException(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue(), "分页获取菜单树形表格内容异常", e);
        }
    }

    /**
     * 修改菜单
     */
    @ResponseBody
    @RequestMapping("/doUpdate")
    @RequiresPermissions(PermissionCode.MENU_UPDATE)
    @Limit(key = AppConst.MENU_UPDATE_LIMIT_KEY, period = AppConst.LIMIT_TIME, count = 1, errMsg = AppConst.UPDATE_LIMIT, limitType = LimitType.IP_AND_METHOD)
    public Object doUpdate(Menu menu) throws MyException {
        try {
            menuService.update(menu);
            return super.success(ResultEnum.UPDATE_SUCCESS.getValue());
        } catch (Exception e) {
            throw new MyException(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue(), "修改菜单异常", e);
        }
    }

    /**
     * 修改菜单状态
     *
     * @param menuId 菜单ID
     * @param status 状态编码
     */
    @ResponseBody
    @RequestMapping("/updateStatus")
    @RequiresPermissions(PermissionCode.MENU_SETSTATUS)
    @Limit(key = AppConst.MENU_UPDATE_STATUS_LIMIT_KEY, period = AppConst.LIMIT_TIME, count = 1, errMsg = AppConst.UPDATE_LIMIT, limitType = LimitType.IP_AND_METHOD)
    public Object updateStatus(Integer menuId, Integer status) throws MyException {
        try {
            Menu menu = menuService.getById(menuId);
            menu.setStatus(status);
            menuService.update(menu);
            return super.success(ResultEnum.UPDATE_SUCCESS.getValue());
        } catch (Exception e) {
            throw new MyException(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue(), "修改菜单状态异常", e);
        }
    }

}
