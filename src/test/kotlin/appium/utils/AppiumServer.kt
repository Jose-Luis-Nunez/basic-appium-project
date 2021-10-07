package appium.utils

import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecuteResultHandler
import org.apache.commons.exec.DefaultExecutor
import java.io.IOException


class AppiumServer {
    fun startServer() {
        val cmd = CommandLine("C:\\Program Files (x86)\\Appium\\node.exe")
        cmd.addArgument("C:\\Program Files (x86)\\Appium\\node_modules\\appium\\bin\\Appium.js")
        cmd.addArgument("--address")
        cmd.addArgument("127.0.0.1")
        cmd.addArgument("--port")
        cmd.addArgument("4723")
        val handler = DefaultExecuteResultHandler()
        val executor = DefaultExecutor()
        executor.setExitValue(1)
        try {
            executor.execute(cmd, handler)
            Thread.sleep(10000)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun stopServer() {
        val runtime = Runtime.getRuntime()
        try {
            runtime.exec("taskkill /F /IM node.exe")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}