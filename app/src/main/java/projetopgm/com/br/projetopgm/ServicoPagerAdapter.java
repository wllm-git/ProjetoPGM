package projetopgm.com.br.projetopgm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ServicoPagerAdapter extends FragmentPagerAdapter {

    private MainActivityFragment mainActivityFragment;
    private MainActivityFotosFragment mainActivityFotosFragment;

    public ServicoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new MainActivityFragment();
        else
            return new MainActivityFotosFragment();

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "info";//Resources.getSystem().getString(R.string.tab_title_info);
            case 1:
                return "fotos";//Resources.getSystem().getString(R.string.tab_title_foto);
        }
        return null;
    }
}
