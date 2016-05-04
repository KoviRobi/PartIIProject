# Build using
# NIXOS_CONFIG="`pwd`/configuration.nix"
# NIX_PATH="nixpkgs=/path/to/nixpkgs/repository/at/revision/32b7b00:$NIX_PATH"
# export NIXOS_CONFIG NIX_PATH
# nixos-rebuild build-vm

# Run with exported shared directory (ideally project root)
# Note, using VM clock, not real clock
# log-in with name: "guest" and password: "vm"
# SHARED_DIR=/home/kr2/Dokumentumok/PartII-Project/part-II-project/
# export SHARED_DIR
# ./result/bin/run-vmhost-vm -rtc 'base=utc,clock=vm'

# Then /tmp/shared is mounted to wherever you have set $SHARED_DIR

{ config, pkgs, ... }:
{ environment.systemPackages = with pkgs; [ jdk maven ];

  fileSystems."/".label = "vmdisk";

  networking.hostName = "vmhost";
  users.extraUsers.guest = {
    isNormalUser = true;
    uid = 1000;
    password = "vm";
    group = "wheel";
  };
  users.extraUsers.root = {
    password = "root";
  };
  security.sudo = {
    enable = true;
    wheelNeedsPassword = false;
  };
  system.stateVersion = "16.09";
}
