define([], function() {
    App.Model._ProductoModel = Backbone.Model.extend({
        defaults: {
 
		 'name' : ''
 ,  
		 'tipo' : ''
 ,  
		 'talla' : ''
 ,  
		 'precio' : ''
 ,  
		 'imagen' : ''
 ,  
		 'calificacion' : ''
 ,  
		 'numCalificaciones' : ''
        },
        initialize: function() {
        },
        getDisplay: function(name) {
         return this.get(name);
        }
    });

    App.Model._ProductoList = Backbone.Collection.extend({
        model: App.Model._ProductoModel,
        initialize: function() {
        }

    });
    return App.Model._ProductoModel;
});