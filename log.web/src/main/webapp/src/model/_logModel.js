define([], function() {
    App.Model._LogModel = Backbone.Model.extend({
        defaults: {
 
		 'name' : ''
 ,  
		 'cantidad' : ''
 ,  
		 'entra' : ''
 ,  
		 'justificacion' : ''
 ,  
		 'bodegaId' : ''
 ,  
		 'productoId' : ''
        },
        initialize: function() {
        },
        getDisplay: function(name) {
			 if(name=='bodegaId'){  
                 var value = App.Utils.getModelFromCache('bodegaComponent',this.get('bodegaId'));
                 if(value) 
                 return value.get('name');
             }
			 if(name=='productoId'){  
                 var value = App.Utils.getModelFromCache('productoComponent',this.get('productoId'));
                 console.log(value.get('talla'));
                 if(value) 
                 return value.get('name') + ' ' + value.get('talla');
             }
                        if(name=='entra'){
                            
                            if(this.get('entra'))
                                return "Entrada";
                            else
                                return "Salida";
                        }
         return this.get(name);
        }
    });

    App.Model._LogList = Backbone.Collection.extend({
        model: App.Model._LogModel,
        initialize: function() {
        }

    });
    return App.Model._LogModel;
});