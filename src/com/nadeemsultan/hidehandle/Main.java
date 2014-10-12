package com.nadeemsultan.hidehandle;

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class Main implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
	private static String MODULE_PATH = null;
	
	@Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }
	
	@Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.android.systemui"))
            return;

        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
        resparam.res.setReplacement("com.android.systemui", "drawable", "status_bar_close_off", modRes.fwd(R.drawable.handle));
        resparam.res.setReplacement("com.android.systemui", "drawable", "status_bar_close_on", modRes.fwd(R.drawable.handle));
    }
}
