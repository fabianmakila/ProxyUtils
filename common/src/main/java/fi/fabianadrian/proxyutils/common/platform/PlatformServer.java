package fi.fabianadrian.proxyutils.common.platform;

import java.util.List;

public interface PlatformServer {

	String name();

	List<PlatformPlayer> players();
}
