define([], function() {
    App.Model._PedidoModel = Backbone.Model.extend({
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
                 return value.get('name') + ' ' + value.get('talla');
             }
         return this.get(name);
        }
    });

    App.Model._PedidoList = Backbone.Collection.extend({
        model: App.Model._PedidoModel,
        initialize: function() {
        }

    });
    return App.Model._PedidoModel;
});