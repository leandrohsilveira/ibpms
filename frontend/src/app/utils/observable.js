import riot from 'riot';

function Observable(initialValue) {

    riot.observable(this);

    this.value = initialValue;

    this.next = (value) => {
        this.value = value;
        this.trigger('next', this.value);
    };

    this.subscribe = (instance, fn) => {

        instance.on('mount', () => {
            this.on('next', onNext);
            fn(this.value)
        });

        instance.on('unmount', () => {
            this.off('next', onNext);
        });

        function onNext(value) {
            console.log('Next observable called', this, value);
            fn(value);
        }

    }

}

export default Observable;