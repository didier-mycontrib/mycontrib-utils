organisation ideale theorique des modules
=========================================

xxx-web ---> xxx-itf
et
xxx-web ---> xxx-exposed-import-yyy 
             où yyy est sring ou cdi-spring ou ...
             (au cas par cas selon technologies ,
              facade spécifique avec ou sans cache
              avec ou sans CDI , ...)
             
xxx-impl-yyy (implemententation effective avec yyy=spring ou ejb3 ou ...)
xxx-delegate (vers ws)            
