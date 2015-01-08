#!/usr/bin/ruby
require 'fileutils'

has_jar = File.exists?(ARGV[0])
abort "Error: Robot code jar doesn't exist at #{ARGV[0]}" unless has_jar

tmp_dir = Dir.pwd + "/tmp"
`rm -rf #{tmp_dir}`

Dir.mkdir(tmp_dir) unless File.exists?(tmp_dir)
FileUtils.cp ARGV[0], tmp_dir + "/FRCUserProgram.jar"

dist_dir = Dir.pwd + "/dist"
FileUtils.cp dist_dir + "/FakeWPILib.jar", tmp_dir + "/FakeWPILib.jar"

Dir.chdir('tmp')
Dir.mkdir('classes')
Dir.chdir('classes')

`jar -xf ../FRCUserProgram.jar`
FileUtils.cp "META-INF/MANIFEST.MF", "META-INF/MANIFEST.MF.old"

`jar -xf ../FakeWPILib.jar`
FileUtils.cp "META-INF/MANIFEST.MF.old", "META-INF/MANIFEST.MF"
FileUtils.rm "META-INF/MANIFEST.MF.old"

Dir.chdir('..')
`jar -cmvf classes/META-INF/MANIFEST.MF to_sim.jar -C classes .`


