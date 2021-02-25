# AutoRegistration
Automatical registration for Bukkit plugins

With this library you don't have to register every command or listener separately.
You only need to use `AutoRegister.registerListeners` or `AutoRegister.registerAutoRegExecutors`

Example:
```
    @Override
    public void onEnable() {
        AutoRegister.registerListeners("me.Dadudze.Listeners");
        AutoRegister.registerAutoRegExecutors("me.Dadudze.Commands");
    }
```

Commands must extend `AutoRegExecutor` for my plugin to know names of the command.
