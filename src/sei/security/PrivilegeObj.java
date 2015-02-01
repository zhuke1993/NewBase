package sei.security;

/**
 * Created by xiong on 2014/10/5.
 */
public class PrivilegeObj {
    public byte doBrowser=0;
    public byte doAdd=0;
    public byte doEdit=0;
    public byte doDelete=0;
    public byte doAudit=0;

    public String browser_scope="";
    public String edit_scope="";
    public String delete_scope="";
    public String audit_scope="";
}
