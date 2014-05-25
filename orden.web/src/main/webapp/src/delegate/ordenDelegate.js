define(['delegate/_ordenDelegate'], function() {
    App.Delegate.OrdenDelegate = App.Delegate._OrdenDelegate.extend(
    
            {
        
        satisfacer : function(id,callback,callbackError){
	    console.log('Satisfacer: '+id);
            $.ajax({
	          url: '/orden.service.subsystem.web/webresources/Orden/'+id+'/satisfacer',
	          type: 'GET',
	          datatype: 'application/json',
	          contentType: 'application/json'
	      }).done(_.bind(function(data){
                  console.log(data);
                  console.log(data['nombre']);
                  console.log(data.get('nombre'));
	    	  callback(data);
	      },this)).error(_.bind(function(data){
	    	  callbackError(data);
	      },this));
	},
        
        nombreProducto : function(id,callback,callbackError){
	    console.log('Satisfacer: '+id);
            $.ajax({
	          url: '/producto.service.subsystem.web/webresources/Producto/'+id,
	          type: 'GET',
	          datatype: 'application/json',
	          contentType: 'application/json'
	      }).done(_.bind(function(data){
                  callback(data.id, data.name);
	      },this)).error(_.bind(function(data){
	    	  callbackError(data);
	      },this));
	}
                
    }
            
            );




});