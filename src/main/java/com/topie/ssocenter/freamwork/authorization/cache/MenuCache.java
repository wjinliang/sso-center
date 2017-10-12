package com.topie.ssocenter.freamwork.authorization.cache;


/**
 * 
 * @author Mr.Jin
 *
 */
public class MenuCache{
	
	/*private static MenuCache instense;
	private BasicCache<String, Object> cache = new OrangeSideNullCache<String, Object>();

	public static MenuCache getInstense(){
		if(instense==null)
			return instense = (MenuCache)AppUtil.getBean("menuCache");
		return instense;
	}
    public void setCache(BasicCache cache) {
        this.cache = cache;
    }
	private void refreshSecurityMenuMapCache() {
		UserRoleService roleService = (UserRoleService) AppUtil.getBean("userroleServiceImpl");
		Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();
        List<Map> roleFunctions = roleService.findUserRoleMatchUpFunctions();
        if (roleFunctions != null && roleFunctions.size() > 0) {
            for (Map roleFunction : roleFunctions) {
                String url = (String) roleFunction.get("function");
                String role = (String) roleFunction.get("role");
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                if (!resourceMap.containsKey(url)) {
                    configAttributes.add(new SecurityConfig(role));
                    resourceMap.put(url, configAttributes);
                } else {
                    ConfigAttribute configAttribute = new SecurityConfig(role);
                    configAttributes = resourceMap.get(url);
                    configAttributes.add(configAttribute);
                    resourceMap.put(url, configAttributes);
                }
            }
        }
        cache.set(OrangeSideSecurityConstant.SECURIYT_MENUMAP_CACHE_KEY, resourceMap);
	}
	public void refreshCache(){
		this.refreshSecurityMenuMapCache();
		this.refreshRoleMenuCache();
	}
	public Map<String, Collection<ConfigAttribute>> getSecurityMenuMapCache(){
		return (Map<String, Collection<ConfigAttribute>>) cache.get(OrangeSideSecurityConstant.SECURIYT_MENUMAP_CACHE_KEY);
	}
	private void refreshRoleMenuCache(){
		UserMenuService menuService = (UserMenuService) AppUtil.getBean("userMenuServiceImpl");
		UserRoleService roleService = (UserRoleService) AppUtil.getBean("userRoleServiceImpl");
		List<UserRole> roles = roleService.selectAll();
		for(UserRole role : roles){
			List<UserMenu> list = menuService.selectMenusByRoleCode(role.getCode());
			cache.set(OrangeSideSecurityConstant.SECURIYT_MENUMAP_CACHE_KEY+role.getCode(), list);
		}
	}
	public List<UserMenu> getMenusByRoleIds(String roleIds) {
		List<UserMenu> menus = null;
		menus= (List<UserMenu>) cache.get(OrangeSideSecurityConstant.SECURIYT_MENUMAP_CACHE_KEY+roleIds);
		if(menus==null){
			menus = new ArrayList<UserMenu>();
			String[] roles = roleIds.split(",");
			for(String id:roles){
				menus.addAll((List<UserMenu>) cache.get(OrangeSideSecurityConstant.SECURIYT_MENUMAP_CACHE_KEY+id));
			}
			//去重
			Set set = new  HashSet(); 
	         List newList = new  ArrayList(); 
	         for (UserMenu cd:menus) {
	            if(set.add(cd.getId())){
	                newList.add(cd);
	            }
	        }
	         menus = newList;
	         //cache.set(OrangeSideSecurityConstant.SECURIYT_MENUMAP_CACHE_KEY+roleIds,menus);
		}
		return menus;
	}*/

}
