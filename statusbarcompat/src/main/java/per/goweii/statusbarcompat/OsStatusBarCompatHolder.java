package per.goweii.statusbarcompat;

/**
 * @author CuiZhen
 * @date 2019/11/17
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
class OsStatusBarCompatHolder {

    private static OsStatusBarCompat sOsStatusBarCompat = null;

    static OsStatusBarCompat get() {
        if (sOsStatusBarCompat == null) {
            if (OsUtils.isMiui()) {
                sOsStatusBarCompat = new OsStatusBarCompatMiui();
            } else if (OsUtils.isFlyme()) {
                sOsStatusBarCompat = new OsStatusBarCompatFlyme();
            } else if (OsUtils.isOppo()) {
                sOsStatusBarCompat = new OsStatusBarCompatOppo();
            } else {
                sOsStatusBarCompat = new OsStatusBarCompatDef();
            }
        }
        return sOsStatusBarCompat;
    }
}
