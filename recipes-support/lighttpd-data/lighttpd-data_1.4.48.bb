SUMMARY = "Lightweight high-performance web server"
HOMEPAGE = "http://www.lighttpd.net/"
BUGTRACKER = "http://redmine.lighttpd.net/projects/lighttpd/issues"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=e4dac5c6ab169aa212feb5028853a579"

SECTION = "net"

SRC_URI = "http://download.lighttpd.net/lighttpd/releases-1.4.x/lighttpd-${PV}.tar.xz \
        file://index.html.lighttpd \
        file://lighttpd.conf \
        file://lighttpd \
        file://lighttpd.service \
        file://0001-Use-pkg-config-for-pcre-dependency-instead-of-config.patch \
        "

SRC_URI[md5sum] = "1e3a9eb5078f481e3a8a1d0aaac8c3c8"
SRC_URI[sha256sum] = "0f8ad5aac7529d7b948b9d7e8cd0b4a9e177309d85d6bf6516e28e6e40d74f36"

PACKAGECONFIG ??= "openssl pcre zlib \
    ${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xattr', 'attr', '', d)} \
"

PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6"
PACKAGECONFIG[mmap] = "--enable-mmap,--disable-mmap"
PACKAGECONFIG[libev] = "--with-libev,--without-libev,libev"
PACKAGECONFIG[mysql] = "--with-mysql,--without-mysql,mariadb"
PACKAGECONFIG[ldap] = "--with-ldap,--without-ldap,openldap"
PACKAGECONFIG[attr] = "--with-attr,--without-attr,attr"
PACKAGECONFIG[valgrind] = "--with-valgrind,--without-valgrind,valgrind"
PACKAGECONFIG[openssl] = "--with-openssl,--without-openssl,openssl"
PACKAGECONFIG[krb5] = "--with-krb5,--without-krb5,krb5"
PACKAGECONFIG[pcre] = "--with-pcre,--without-pcre,libpcre"
PACKAGECONFIG[zlib] = "--with-zlib,--without-zlib,zlib"
PACKAGECONFIG[bzip2] = "--with-bzip2,--without-bzip2,bzip2"
PACKAGECONFIG[fam] = "--with-fam,--without-fam,gamin"
PACKAGECONFIG[webdav-props] = "--with-webdav-props,--without-webdav-props,libxml2 sqlite3-data"
PACKAGECONFIG[webdav-locks] = "--with-webdav-locks,--without-webdav-locks,util-linux"
PACKAGECONFIG[gdbm] = "--with-gdbm,--without-gdbm,gdbm"
PACKAGECONFIG[memcache] = "--with-memcached,--without-memcached,libmemcached"
PACKAGECONFIG[lua] = "--with-lua,--without-lua,lua5.1"

EXTRA_OECONF += "--enable-lfs"

inherit autotools pkgconfig update-rc.d gettext systemd

INITSCRIPT_NAME = "lighttpd"
INITSCRIPT_PARAMS = "defaults 70"

SYSTEMD_SERVICE_${PN} = "lighttpd.service"

addtask do_deploy_source_date_epoch before do_patch after do_unpack
do_deploy_source_date_epoch(){
		cp -r  ${WORKDIR}/lighttpd-1.4.48/* ${WORKDIR}/lighttpd-data-1.4.48
}

do_install_append() {
	install -d ${D}${sysconfdir}/init.d ${D}${sysconfdir}/lighttpd.d ${D}/www/pages/dav ${D}/data/var
	install -m 0755 ${WORKDIR}/lighttpd ${D}${sysconfdir}/init.d
	install -m 0644 ${WORKDIR}/lighttpd.conf ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/index.html.lighttpd ${D}/www/pages/index.html

	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/lighttpd.service ${D}${systemd_unitdir}/system
	sed -i -e 's,@SBINDIR@,${sbindir},g' \
		-e 's,@SYSCONFDIR@,${sysconfdir},g' \
		-e 's,@BASE_BINDIR@,${base_bindir},g' \
		${D}${systemd_unitdir}/system/lighttpd.service
	#For FHS compliance, create symbolic links to /var/log and /var/tmp for logs and temporary data
	ln -sf ${localstatedir}/log ${D}/www/logs
	#ln -sf ${localstatedir}/tmp ${D}/www/var
	#cp -rf ${localstatedir}/tmp/* ${D}/data/var
	#rm -rf ${D}/www/var/*
	ln -sf /data/www/var ${D}/www/var

	# added for data partition
	install -d ${D}/data/lib
	install -d ${D}/usr/lib
	cp -r ${D}/usr/lib/mod_access.so ${D}/data/lib/mod_access.so
	cp -r ${D}/usr/lib/mod_accesslog.so ${D}/data/lib/mod_accesslog.so
	cp -r ${D}/usr/lib/mod_dirlisting.so ${D}/data/lib/mod_dirlisting.so
	cp -r ${D}/usr/lib/mod_indexfile.so ${D}/data/lib/mod_indexfile.so
	cp -r ${D}/usr/lib/mod_staticfile.so ${D}/data/lib/mod_staticfile.so
	cd ${D}/usr/lib/
	rm -rf mod_access.so mod_accesslog.so mod_dirlisting.so mod_indexfile.so mod_staticfile.so
	ln -s ./../../data/lib/mod_access.so mod_access.so
	ln -s ./../../data/lib/mod_accesslog.so mod_accesslog.so
	ln -s ./../../data/lib/mod_dirlisting.so mod_dirlisting.so
	ln -s ./../../data/lib/mod_indexfile.so mod_indexfile.so
	ln -s ./../../data/lib/mod_staticfile.so mod_staticfile.so
}

FILES_${PN} += "${sysconfdir} /www /data/lib/mod_access.so /data/lib/mod_accesslog.so /data/lib/mod_dirlisting.so /data/lib/mod_indexfile.so /data/lib/mod_staticfile.so /usr/lib/mod_access.so /usr/lib/mod_accesslog.so /usr/lib/mod_dirlisting.so /usr/lib/mod_indexfile.so /usr/lib/mod_staticfile.so"

INSANE_SKIP_${PN} = " \
    ldflags \
    installed-vs-shipped \
"

CONFFILES_${PN} = "${sysconfdir}/lighttpd.conf"

#PACKAGES_DYNAMIC += "^lighttpd-module-.*"

FILES_${PN}-dbg += "/usr/lib/mod_access.so /usr/lib/mod_accesslog.so /usr/lib/mod_dirlisting.so /usr/lib/mod_indexfile.so /usr/lib/mod_staticfile.so"
