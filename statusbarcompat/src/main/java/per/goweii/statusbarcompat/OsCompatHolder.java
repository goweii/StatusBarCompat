package per.goweii.statusbarcompat;

import per.goweii.statusbarcompat.compat.OsCompat;
import per.goweii.statusbarcompat.compat.OsCompatDef;
import per.goweii.statusbarcompat.compat.OsCompatFlyme;
import per.goweii.statusbarcompat.compat.OsCompatMiui;
import per.goweii.statusbarcompat.compat.OsCompatOppo;
import per.goweii.statusbarcompat.utils.OsUtils;

/**
 * @author CuiZhen
 * @date 2019/11/17
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
class OsCompatHolder {

    private static OsCompat sOsCompat = null;

    static OsCompat get() {
        if (sOsCompat == null) {
            if (OsUtils.isMiui()) {
                sOsCompat = new OsCompatMiui();
            } else if (OsUtils.isFlyme()) {
                sOsCompat = new OsCompatFlyme();
            } else if (OsUtils.isOppo()) {
                sOsCompat = new OsCompatOppo();
            } else {
                sOsCompat = new OsCompatDef();
            }
        }
        return sOsCompat;
    }
}
