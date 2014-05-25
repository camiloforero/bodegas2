define([], function() {
    App.Model._OrdenModel = Backbone.Model.extend({
        defaults: {
            
            'name' : ''
            ,  
            'tipo' : ''
            ,  
            'estado' : ''
            ,  
            'cantidad' : ''
        },
        initialize: function() {
        },
        getDisplay: function(name) {
            if(name=='name'){  
                if(name=='name'){  
                    var id = parseInt(this.get('name'));
                    var cambiarNombre = function(id, string)
                    {
                        $("td[productoID='" + id + "']").html(string);
                    };
                    App.Delegate.OrdenDelegate.prototype.nombreProducto(id, cambiarNombre, function(){});
                    
                }
            }
            return this.get(name);
        }
    });
    
    App.Model._OrdenList = Backbone.Collection.extend({
        model: App.Model._OrdenModel,
        initialize: function() {
        }
        
    });
    return App.Model._OrdenModel;
});