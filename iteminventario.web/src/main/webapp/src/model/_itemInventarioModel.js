define([], function() {
    App.Model._ItemInventarioModel = Backbone.Model.extend({
        defaults: {
 
		 'cantidad' : ''
 ,  
		 'name' : ''
 ,  
		 'productoId' : ''
        },
        initialize: function() {
        },
        getDisplay: function(name) {
			 if(name=='productoId'){  
                 var value = App.Utils.getModelFromCache('productoComponent',this.get('productoId'));
                 if(value) 
                 return value.get('name');
             }
                        if(name=='name'){  
                 var id = parseInt(this.get('name'));
                 alert(id);
                 var value = App.Utils.getModelFromCache('bodegaComponent',id);
                 if(value) 
                 return value.get('name');
             }
         return this.get(name);
        }
    });

    App.Model._ItemInventarioList = Backbone.Collection.extend({
        model: App.Model._ItemInventarioModel,
        initialize: function() {
        }

    });
    return App.Model._ItemInventarioModel;
});