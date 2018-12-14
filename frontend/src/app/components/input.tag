import SimpleMaskMoney from 'simple-mask-money';
import maskConfig from '../config/mask';

<app-input>

    <div class={'field': true, 'error': validated && !valid}>
        <label for={opts.name}>{opts.label}</label>
        <input 
            type={opts.type} 
            name={opts.name} 
            class={'app-input-money': opts.money !== undefined} 
            inputmode={opts.inputmode} 
            value={value} 
            onchange={handleChange}
        >
        <span if={validated && !valid} class="msg">{error}</span>
    </div>

    <style type="scss">
        :scope {
            > .field {
                &.error {
                    color: red;
                }

                &.error > select,
                &.error > input {
                    color: red;
                    border-color: red;
                    margin-bottom: 5px;
                }

                > .msg {
                    margin-bottom: 10px;
                }
            }

        }
    </style>

    <script>

        this.maskedRef = null;

        this.error = null;
        this.value = '';
        this.valid = false;
        this.validated = false;

        this.validate = () => {
            let error = null;
            if(opts.required !== undefined && !this.value) {
                error = 'Campo obrigatório'
            }
            this.update({error, validated: true, valid: !error});
            return !error;
        };
        
        this.handleChange = (e) => {
            let value = e.target.value;
            if(opts.money !== undefined) {
                value = this.maskedRef.formatToNumber();
            }
            this.update({ value });
        };

        this.on('mount', () => {
            if(opts.initialvalue) {
                this.update({
                    value: opts.initialvalue
                });
            }
            if(opts.money !== undefined) {
                this.maskedRef = SimpleMaskMoney.setMask('.app-input-money', maskConfig);
            }
        });

    </script>
</app-input>