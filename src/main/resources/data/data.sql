-- insert initial data
-- TABLE_NAME is the name used in the declaration of the table at the entities, COLUMN_NAME the names the ones given
-- to the database in the (name=xxx) field. Don't forget the prefixes here!
-- format:
-- insert into TABLE_NAME (COLUMN_NAME, COLUMN_NAME2,...) values (VALUE1, VALUE2, ...);
INSERT into forum_category(F_CAT_NAME ) values ('foo');
INSERT into seminar_category(S_CAT_NAME) values ( 'Betroffene helfen Betroffenen' );
INSERT into seminar_category(S_CAT_NAME) values ( 'Angehörige erzählen' );
INSERT into seminar_category(S_CAT_NAME) values ( 'Ärzte geben Auskunft' );
INSERT into seminar_category(S_CAT_NAME) values ( 'Allgemeines' );
INSERT into seminar(SEM_STREET, SEM_HOUSE_NUMBER, SEM_PLZ, SEM_LOCATION, SEM_TITLE, SEM_DATE, SEM_CATEGORY, SEM_URL,
                    SEM_DESCRIPTION)
      values ('Wankdorffeldstrasse','102',3014,'Bern', 'Wie unterstütze ich einen Angehörigen?',
              parsedatetime('2019-11-07 13:30:00.0', 'yyyy-MM-dd hh:mm:ss.SS'), 3,
              'https://vaadin.com','Ärzte berichten von ihren Erlebnissen mit der Krankheit. Dr. Luis Alvador gibt Ihnen ' ||
              'einen Überblick über die medizinischen Informationen, eine Podiumsdiskussion mit Angehörigen findet statt.');
INSERT into seminar(SEM_STREET, SEM_HOUSE_NUMBER, SEM_PLZ, SEM_LOCATION, SEM_TITLE, SEM_DATE, SEM_CATEGORY, SEM_URL,
                    SEM_DESCRIPTION)
      values ('Schanzenstrasse','5',3008,'Bern', 'Sozialphobie - Mit der Angst umgehen',
              parsedatetime('2019-08-07 10:30:00.0', 'yyyy-MM-dd hh:mm:ss.SS'), 1,
              'https://www.nzz.ch/','Betroffene erzählen von Ihren Problemen im Umgang mit der Krankheit und zeigen wie ' ||
                                    'Sie diese tagtäglich überwinden.');
INSERT into seminar(SEM_STREET, SEM_HOUSE_NUMBER, SEM_PLZ, SEM_LOCATION, SEM_TITLE, SEM_DATE, SEM_CATEGORY, SEM_URL,
                    SEM_DESCRIPTION)
      values ('Bachweg','18b',3852,'Rothrist', 'Angehörigentreffen',
              parsedatetime('2019-09-09 20:00:00.0', 'yyyy-MM-dd hh:mm:ss.SS'), 2,
              'https://www.zeit.de/index','Wir haben zehn Angehörige von Personen mit Sozialphobie eingeladen, die ' ||
               'Ihnen von Ihren Erlebnissen erzählen. Treffen Sie uns zu einem Feierabendbier. Mehr unter dem Link.');
INSERT into seminar(SEM_STREET, SEM_HOUSE_NUMBER, SEM_PLZ, SEM_LOCATION, SEM_TITLE, SEM_DATE, SEM_CATEGORY, SEM_URL,
                    SEM_DESCRIPTION)
      values ('Badenerstrasse','420',8040,'Zürich', 'Betroffenenhöck',
              parsedatetime('2020-02-15 09:00:00.0', 'yyyy-MM-dd hh:mm:ss.SS'), 1,
              'https://de.wikipedia.org/wiki/Soziale_Phobie','Ein Treffen mit anderen Betroffenen. Hier können Sie sich ' ||
              'entspannt fühlen, niemand wird sie verurteilen, da wir dasselbe tagtäglich auch durchmachen.');
INSERT into user(USER_USERNAME, USER_EMAIL, USER_PASSWORD, USER_PERMISSION) values
                ('Lara', 'nomail@nomailhausen.com', '12345', 1);
                INSERT into user(USER_USERNAME, USER_EMAIL, USER_PASSWORD, USER_PERMISSION) values
                ('Admin', 'nomail@nomailhausen.com', 'admin', 4);