package com.example.realpg.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.realpg.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

/*
 * Pasos para meter una pagina nueva a la interfaz principal
 * (No incluye los pasos para meter la nueva opcion tambien en la navbar. Esto parte de que eso ya esta hecho)
 * 1-Crear archivo xml copiando el fragment_main2.xml poniendole el nombre que queramos
 * 2-Crear un archivo java en UI copiando el archivo PlaceholderFragment2
 * 3-En el archivo xml cambiar (viendo su codigo) tools:context=".ui.main.NombreACambiar">
 * Sustituir NombreACambiar por el nombre de la clase java
 * 4-En esta clase modificar getItem
 * Si estas metiendo la pagina 2 (position 1) poner que si position == 1 haga un return
 * de tu nueva clase java .newIntance(position+1)
 * Esta ultima cosa podria no ser necesario, pero asi reutilizamos el codigo de la plantilla
 * 5-Ir a nuestra nueva clase java y hacer los siguientes cambios
 * 6-Cambiar el nombre de tipo de la variable private FragmentMain2Binding binding;
 * el nombre de tipo debera coincidir con el del xml que creamos en camelCase y acabando en Biding
 * (nos pedira importar dicha clase si lo hemos escrito bien)
 * 7-En la funcion onCreateView cambiamos la linea  binding = FragmentMain2Binding.inflate(inflater, container, false);
 * Cambiamos el nombre de FragmentMain2Binding por el mismo que escribimos en el paso anterior
 *
 * Nota: La clase pageViewModel ya no sera necesaria ya que ya no estamos poniendo la misma vista
 * con diferentes datos, sino vistas completamente diferentes, perdiendo asi su utilidad
 *
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0)
        {
            Page1 p1 = Page1.newInstance(1);
            return p1;
        }
        else if(position == 1)
        {
            Page2 p2 = Page2.newInstance(2);

            return p2;
        }

            //return PlaceholderFragment2.newInstance(2);
        else {
            Page3 p = Page3.newInstance(3, mContext);
            //p.update();
            return Page3.newInstance(3, mContext);
            //return PlaceholderFragment.newInstance(position + 1);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return TAB_TITLES.length;
    }
}