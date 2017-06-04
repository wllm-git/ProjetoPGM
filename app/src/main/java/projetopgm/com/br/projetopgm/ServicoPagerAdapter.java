package projetopgm.com.br.projetopgm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import projetopgm.com.br.projetopgm.base.Servico;

public class ServicoPagerAdapter extends FragmentPagerAdapter {

    private Servico servico;
    private MainActivityFragment mainActivityFragment;
    private MainActivityFotosFragment mainActivityFotosFragment;

    public ServicoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            mainActivityFragment = new MainActivityFragment();
            mainActivityFragment.adicionarServico(servico);
            return mainActivityFragment;
        }
        else{
            mainActivityFotosFragment = new MainActivityFotosFragment();
            mainActivityFotosFragment.adicionarFotos(servico);
            return mainActivityFotosFragment;
        }
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

    public void adicionarServico(Servico servico){
        this.servico = servico;

        if(servico == null)
            return;

        if(mainActivityFotosFragment != null)
            mainActivityFotosFragment.adicionarFotos(servico);
        if(mainActivityFragment != null)
            mainActivityFragment.adicionarServico(servico);
    }
}
