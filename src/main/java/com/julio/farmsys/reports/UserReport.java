package com.julio.farmsys.reports;

import java.util.List;

import com.julio.farmsys.model.User;
public class UserReport extends Report {

    private List<User> users;

    public UserReport( List<User> users ) {
        super( "Relatorio de Usuarios" );
        this.tableHeaders = new String[] { "ID", "Nome", "E-mail", "Ativo" };
        this.tableWidths = new float[] { 0.1f, 0.4f, 0.4f, 0.1f };
        this.users = users;
    }

    @Override
    public void generateData() {

        for (User user : users) {
            table.addCell( String.valueOf( user.getId() ) );
            table.addCell( user.getName() );
            table.addCell( user.getEmail() );
            table.addCell( String.valueOf( user.isEnabled() ) );
        }
    }
}
